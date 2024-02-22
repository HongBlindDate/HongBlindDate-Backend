package hongblinddate.backend.domain.member.controller;

import hongblinddate.backend.domain.member.domain.Report;
import hongblinddate.backend.domain.member.dto.request.ReportDetails;
import hongblinddate.backend.domain.member.dto.request.ReportResolutionRequest;
import hongblinddate.backend.domain.member.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping // 신고 생성 API
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report newReport = reportService.createReport(report.getReportType(), report.getReportedMember().getId(), report.getReportedBy().getId());
        return ResponseEntity.ok(newReport);
    }


    @GetMapping // 신고 전체 조회 API
    public ResponseEntity<List<ReportDetails>> getAllReports() {
        List<ReportDetails> reportsWithSuspension = reportService.getAllReports();
        return ResponseEntity.ok(reportsWithSuspension);
    }

    @PostMapping("/{reportId}/resolve") // 신고 해결 API. 신고의 상태를 대기에서 처리나 기각으로 변경.
    public ResponseEntity<?> resolveReport(@PathVariable Long reportId,
                                           @RequestBody ReportResolutionRequest request) {
        reportService.resolveReport(reportId, request.getStatus());
        return ResponseEntity.ok().build();
    }
}
