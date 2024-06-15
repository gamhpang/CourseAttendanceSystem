package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.LocationType;
import lombok.Data;

@Data
public class LocationDTO {

    private long locationId;
    private String name;
    private int capacity;


}
