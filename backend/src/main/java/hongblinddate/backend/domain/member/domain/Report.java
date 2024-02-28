package hongblinddate.backend.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportType reportType; // 신고 유형(프로필, 사진, 채팅)

    @Enumerated(EnumType.STRING)
    private ReportStatus status; // 처리 상태(대기, 처리, 기각)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_member_id")
    private Member reportedBy; // 신고자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id")
    private Member reportedMember; // 피신고자

    public Report(ReportType reportType, Member reportedBy, Member reportedMember) {
        this.reportType = reportType;
        this.reportedBy = reportedBy;
        this.reportedMember = reportedMember;
        this.status = ReportStatus.PENDING; // default status
    }

    public void resolve(ReportStatus newStatus) {
        if (this.status == ReportStatus.PENDING || this.status == ReportStatus.DISMISSED) {
            this.status = newStatus;
        } else {
            throw new IllegalStateException("Report can only be resolved or dismissed from a PENDING or DISMISSED status.");
        }
    }
}

