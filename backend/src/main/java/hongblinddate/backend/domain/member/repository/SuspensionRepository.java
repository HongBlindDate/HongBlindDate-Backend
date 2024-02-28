package hongblinddate.backend.domain.member.repository;

import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.domain.Suspension;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspensionRepository extends JpaRepository<Suspension, Long> {
    @Query("SELECT SUM(s.suspensionPeriod) FROM Suspension s WHERE s.member = :member")
    Integer sumSuspensionPeriodsForMember(@Param("member") Member member);
}
