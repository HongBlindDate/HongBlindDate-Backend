package hongblinddate.backend.domain.member.service;

import hongblinddate.backend.domain.member.domain.Grade;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final MemberRepository memberRepository;

    @Autowired
    public AdminService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getPendingApplicants() {
        return memberRepository.findByGrade(Grade.APPLICANT);
    }

    @Transactional
    public void approveOrRejectProfile(Long memberId, boolean isApproved) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        if (isApproved) {
            member.updateGrade(Grade.MEMBER);
        }
    }
}
