package hongblinddate.backend.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;


import hongblinddate.backend.common.jwt.service.JwtService;
import hongblinddate.backend.domain.member.domain.Grade;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.domain.Suspension;
import hongblinddate.backend.domain.member.repository.MemberRepository;
import hongblinddate.backend.domain.member.repository.SuspensionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({SuspensionService.class})
public class SuspensionServiceTest {

    @Autowired
    private SuspensionService suspensionService;

    @MockBean
    private SuspensionRepository suspensionRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private JwtService jwtService;

    private Member member;
    private Member suspendedMember;
    private Suspension suspension;

    @BeforeEach
    void setUp() {
        member = Member.createTestMember("testAccount", "testPassword", "testEmail", "testNickName", Grade.MEMBER);
        suspension = new Suspension(member, LocalDate.now(), 30);
        suspendedMember = Member.createTestMember("testAccount", "testPassword", "testEmail", "testNickName", Grade.BANNED);
        suspension = new Suspension(suspendedMember, LocalDate.now().minusDays(31), 30); // 30일간의 정지 기간이 이미 지난 상태
    }

    @Test
    void testSuspendUser() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(suspensionRepository.save(any(Suspension.class))).thenReturn(suspension);

        Suspension result = suspensionService.suspendUser(1L, 30);

        assertNotNull(result);
        assertEquals(member, result.getMember());
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void testCheckAndResolveSuspensions() {
        when(suspensionRepository.findAll()).thenReturn(List.of(suspension));
        when(memberRepository.save(any(Member.class))).then(returnsFirstArg());

        suspensionService.checkAndResolveSuspensions();

        assertEquals(Grade.MEMBER, suspendedMember.getGrade());
        verify(memberRepository, times(1)).save(suspendedMember);
    }
}