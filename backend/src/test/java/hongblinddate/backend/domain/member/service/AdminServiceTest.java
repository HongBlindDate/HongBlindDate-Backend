package hongblinddate.backend.domain.member.service;

import hongblinddate.backend.common.jwt.service.JwtService;
import hongblinddate.backend.domain.member.domain.Grade;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminServiceTest {

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private AdminService adminService;

    private Member testMember;

    @MockBean
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        // 테스트용 멤버 초기화
        testMember = Member.createTestMember("testAccount", "testPassword", "test@example.com", "testNickName", Grade.APPLICANT);
    }

    // 프로필 인증 대기자 명단 조회 테스트
    @Test
    void getPendingApplicantsTest() {
        // given
        when(memberRepository.findByGrade(Grade.APPLICANT)).thenReturn(Arrays.asList(testMember));

        // when
        List<Member> result = adminService.getPendingApplicants();

        // then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(Grade.APPLICANT, result.get(0).getGrade());

        // verify
        verify(memberRepository, times(1)).findByGrade(Grade.APPLICANT);
    }

    // 프로필 인증 테스트
    @Test
    void approveOrRejectProfileTest_Approve() {
        // given
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(testMember));

        // when
        adminService.approveOrRejectProfile(memberId, true);

        // then
        assertEquals(Grade.MEMBER, testMember.getGrade());

        // verify
        verify(memberRepository, times(1)).findById(memberId);
    }

    // 프로필 인증 반려 테스트
    @Test
    void approveOrRejectProfileTest_Reject() {
        // given
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(testMember));

        // when
        adminService.approveOrRejectProfile(memberId, false);

        // then
        assertEquals(Grade.APPLICANT, testMember.getGrade());

        // verify
        verify(memberRepository, times(1)).findById(memberId);
    }
}
