package hongblinddate.backend.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Suspension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suspension_id")
    private Long id;

    private LocalDate startDate;

    private Integer suspensionPeriod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    public Suspension(Member member, LocalDate startDate, int suspensionPeriod) {
        this.member = member;
        this.startDate = startDate;
        this.suspensionPeriod = suspensionPeriod;
    }
}

