package hongblinddate.backend.domain.member.service;

import hongblinddate.backend.common.exception.MemberNotFoundException;
import hongblinddate.backend.common.exception.ReportNotFoundException;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.domain.Report;
import hongblinddate.backend.domain.member.domain.ReportStatus;
import hongblinddate.backend.domain.member.domain.ReportType;
import hongblinddate.backend.domain.member.dto.request.ReportDetails;
import hongblinddate.backend.domain.member.repository.MemberRepository;
import hongblinddate.backend.domain.member.repository.ReportRepository;
import hongblinddate.backend.domain.member.repository.SuspensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final SuspensionRepository suspensionRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository, MemberRepository memberRepository, SuspensionRepository suspensionRepository) {
        this.reportRepository = reportRepository;
        this.memberRepository = memberRepository;
        this.suspensionRepository = suspensionRepository;
    }

    public List<ReportDetails> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(report -> new ReportDetails(report, calculateTotalSuspensionDays(report.getReportedMember())))
                .collect(Collectors.toList());
    }

    private Integer calculateTotalSuspensionDays(Member member) {
        return suspensionRepository.sumSuspensionPeriodsForMember(member);
    }

    public Report createReport( ReportType reportType, Long reportedById, Long reportedMemberId) {
        Member reportedBy = memberRepository.findById(reportedById)
                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);
        Member reportedMember = memberRepository.findById(reportedMemberId)
                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);

        Report report = new Report(reportType, reportedMember, reportedBy);
        return reportRepository.save(report);
    }

    public void resolveReport(Long reportId, ReportStatus status) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with id: " + reportId));
        report.resolve(status);
        reportRepository.save(report);
    }

}
