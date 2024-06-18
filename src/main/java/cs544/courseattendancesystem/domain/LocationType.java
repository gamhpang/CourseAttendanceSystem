package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LocationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;

    @Embedded
    private AuditData auditData;

    public LocationType(){}
    public LocationType(String type) {
        this.type = type;
    }
}
