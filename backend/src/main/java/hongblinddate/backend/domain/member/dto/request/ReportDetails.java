package hongblinddate.backend.domain.member.dto.request;

import hongblinddate.backend.domain.member.domain.Report;
import lombok.Getter;

@Getter
public class ReportDetails {
    private final Report report;
    private final Integer totalSuspensionDays;

    public ReportDetails(Report report, Integer totalSuspensionDays) {
        this.report = report;
        this.totalSuspensionDays = totalSuspensionDays != null ? totalSuspensionDays : 0;
    }
}