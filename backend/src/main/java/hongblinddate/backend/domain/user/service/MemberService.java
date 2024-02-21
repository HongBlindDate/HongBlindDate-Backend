package hongblinddate.backend.domain.user.service;

import hongblinddate.backend.common.exception.EmailNotFoundException;
import hongblinddate.backend.domain.user.domain.Grade;
import hongblinddate.backend.domain.user.domain.Member;
import hongblinddate.backend.domain.user.dto.request.JoinRequest;
import hongblinddate.backend.domain.user.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(@RequestBody @Valid JoinRequest joinRequest) {
        memberRepository.findByEmail(joinRequest.getEmail()).orElseThrow(() -> EmailNotFoundException.EXCEPTION);

        Member member = Member.create(joinRequest, Grade.APPLICANT);
        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);
    }
}
