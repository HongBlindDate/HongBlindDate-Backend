package hongblinddate.backend.domain.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import hongblinddate.backend.common.jwt.service.JwtService;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.domain.Report;
import hongblinddate.backend.domain.member.domain.ReportStatus;
import hongblinddate.backend.domain.member.domain.ReportType;
import hongblinddate.backend.domain.member.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ReportService.class})
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private ReportRepository reportRepository;

    @Test
    void testResolveReport() {
        // Given
        Long reportId = 1L;
        Report report = new Report(ReportType.CHAT, mock(Member.class), mock(Member.class));
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        // When
        reportService.resolveReport(reportId, ReportStatus.RESOLVED);

        // Then
        assertEquals(ReportStatus.RESOLVED, report.getStatus());
        verify(reportRepository, times(1)).save(report);
    }
}