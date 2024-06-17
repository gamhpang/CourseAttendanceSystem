package cs544.courseattendancesystem.service.dto;

import lombok.Data;

@Data
public class LocationDTO {

    private long locationId;
    private String name;
    private int capacity;
    private long typeId;


}
