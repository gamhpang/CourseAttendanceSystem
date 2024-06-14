package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Location {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private int capacity;
    @Embedded
    private AuditData auditData;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private LocationType locationType;

    protected Location(){}
    public Location(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
}
