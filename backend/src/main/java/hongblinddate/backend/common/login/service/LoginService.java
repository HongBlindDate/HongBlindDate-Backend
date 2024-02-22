package hongblinddate.backend.common.login.service;

import hongblinddate.backend.common.exception.AccountNotFoundException;
import hongblinddate.backend.domain.user.domain.Member;
import hongblinddate.backend.domain.user.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginService implements UserDetailsService {

    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String account){
        Member member = userRepository
                .findByAccount(account).orElseThrow(() -> AccountNotFoundException.EXCEPTION);

        return User.builder()
                .username(member.getAccount())
                .password(member.getPassword())
                .roles(member.getGrade().getDescription())
                .build();
//        return new MemberDetail(member);
    }
}
