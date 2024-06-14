package cs544.courseattendancesystem.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LocationType {
    @Id
    @GeneratedValue
    private long id;

    private String type;
    @Embedded
    private AuditData auditData;

    protected LocationType(){}
    public LocationType(String type) {
        this.type = type;
    }
}
