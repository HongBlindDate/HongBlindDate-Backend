package hongblinddate.backend.domain.member.service;

import hongblinddate.backend.common.exception.AccountDuplicationException;
import hongblinddate.backend.domain.member.domain.Grade;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.dto.request.JoinRequest;
import hongblinddate.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	@Transactional
	public void create(JoinRequest joinRequest) {
		if (memberRepository.findByAccount(joinRequest.getAccount()).isPresent()) {
			throw AccountDuplicationException.EXCEPTION;
		}

		Member member = Member.create(joinRequest, Grade.APPLICANT);
		member.passwordEncode(passwordEncoder);
		memberRepository.save(member);
	}
}
