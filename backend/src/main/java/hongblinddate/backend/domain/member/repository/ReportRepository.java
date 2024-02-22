package hongblinddate.backend.domain.member.repository;

import hongblinddate.backend.domain.member.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
