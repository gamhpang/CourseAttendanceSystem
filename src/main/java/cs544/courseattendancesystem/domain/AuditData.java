package cs544.courseattendancesystem.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Data
public class AuditData {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String createdBy;
    private String updatedBy;


}
