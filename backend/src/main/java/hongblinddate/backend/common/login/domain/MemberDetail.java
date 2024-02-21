package hongblinddate.backend.common.login.domain;

import hongblinddate.backend.domain.user.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MemberDetail implements UserDetails {

    private Member member;

    public MemberDetail(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료여부 제공
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 비활성화 여부 제공
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 계정 인증 정보를 항상 저장할지에 대한 여부
        return false;
    }

    @Override
    public boolean isEnabled() {
        //계정 활성화 여부
        return false;
    }
}
