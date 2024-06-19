package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.AuditData;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
public class LocationTypeDTO {
    private long id;

    private String type;
}
