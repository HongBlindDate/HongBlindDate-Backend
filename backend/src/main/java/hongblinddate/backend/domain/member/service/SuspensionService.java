package hongblinddate.backend.domain.member.service;

import hongblinddate.backend.common.exception.MemberNotFoundException;
import hongblinddate.backend.domain.member.domain.Grade;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.domain.Suspension;
import hongblinddate.backend.domain.member.repository.MemberRepository;
import hongblinddate.backend.domain.member.repository.SuspensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class SuspensionService {

    private static final int PERMANENT_SUSPENSION_PERIOD_DAYS = 365 * 100; // Representing "permanent" as 100 years

    private final SuspensionRepository suspensionRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public SuspensionService(SuspensionRepository suspensionRepository, MemberRepository memberRepository) {
        this.suspensionRepository = suspensionRepository;
        this.memberRepository = memberRepository;
    }

    // 회원 정지
    public Suspension suspendUser(Long memberId, int days) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        LocalDate startDate = LocalDate.now();

        int effectiveDays = days == -1 ? PERMANENT_SUSPENSION_PERIOD_DAYS : days; // -1은 영구 정지를 나타냄
        Suspension suspension = new Suspension(member, startDate, effectiveDays);
        suspensionRepository.save(suspension);

        member.updateGrade(Grade.BANNED); // 정지 시 회원 등급 BANNED
        memberRepository.save(member);

        return suspension;
    }

    // 매일 정지 상태를 확인하고 정지 기간 초과 시 정지 해제
    @Scheduled(fixedRate = 86400000) // 1일,
    public void checkAndResolveSuspensions() {
        LocalDate today = LocalDate.now();
        suspensionRepository.findAll().forEach(suspension -> {
            LocalDate endDate = suspension.getStartDate().plusDays(suspension.getSuspensionPeriod());
            if (endDate.isBefore(today)) {
                Member member = suspension.getMember();
                member.updateGrade(Grade.MEMBER); // 정지 해제 시 회원 등급 MEMBER
                memberRepository.save(member);
            }
        });
    }
}