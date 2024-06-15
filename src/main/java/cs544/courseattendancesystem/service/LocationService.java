package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.service.dto.LocationDTO;

import java.util.List;

public interface LocationService {

    LocationDTO createLocation(String name, int capacity);

    LocationDTO updateLocation(LocationDTO locationDTO);

    LocationDTO getLocation(long locationId);

    List<LocationDTO> getAllLocations();

    void deleteLocation(long locationId);

}
