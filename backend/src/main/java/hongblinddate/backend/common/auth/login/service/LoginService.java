package hongblinddate.backend.common.auth.login.service;

import hongblinddate.backend.common.exception.AccountNotFoundException;
import hongblinddate.backend.common.auth.login.domain.MemberDetails;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginService implements UserDetailsService {

	private final MemberRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String account) {
		Member member = userRepository
			.findByAccount(account).orElseThrow(() -> AccountNotFoundException.EXCEPTION);

		return new MemberDetails(member);
	}
}
