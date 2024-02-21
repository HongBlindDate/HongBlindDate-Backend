package hongblinddate.backend.domain.user.repository;

import hongblinddate.backend.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByAccount(String account);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickName(String nickname);
    Optional<Member> findByRefreshToken(String refreshToken);
}
