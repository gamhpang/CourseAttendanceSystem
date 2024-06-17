package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Location;
import cs544.courseattendancesystem.service.dto.LocationDTO;

import java.util.List;

public interface LocationService {

    LocationDTO createLocation(String name, int capacity, Long locationTypeId);

    LocationDTO updateLocation(LocationDTO locationDTO);

    LocationDTO getLocation(long locationId);

    Location getLocationById(long locationId);

    List<LocationDTO> getAllLocations();

    void deleteLocation(long locationId);

}
