package hongblinddate.backend.domain.member.dto.request;

import hongblinddate.backend.domain.member.domain.ReportStatus;
import lombok.Getter;

@Getter
public class ReportResolutionRequest {
    private ReportStatus status;
    private String actionTaken;
}
