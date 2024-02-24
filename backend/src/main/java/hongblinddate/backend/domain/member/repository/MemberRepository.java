package hongblinddate.backend.domain.member.repository;

import hongblinddate.backend.domain.member.domain.Grade;
import hongblinddate.backend.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByAccount(String account);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickName(String nickname);
    Optional<Member> findByRefreshToken(String refreshToken);
    List<Member> findByGrade(Grade grade);
}
