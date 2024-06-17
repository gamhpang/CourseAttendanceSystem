package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Location;
import cs544.courseattendancesystem.service.dto.LocationDTO;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter {


    public static LocationDTO getLocationDTOFromLocation(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(location.getId());
        locationDTO.setName(location.getName());
        locationDTO.setCapacity(location.getCapacity());
        locationDTO.setTypeId(location.getLocationType().getId());
        return locationDTO;
    }

    public static List<LocationDTO> getLocationDTOsFromLocations(List<Location> locations) {
        List<LocationDTO> locationDTOs = new ArrayList<>();
        for (Location location : locations) {
            locationDTOs.add(getLocationDTOFromLocation(location));
        }
        return locationDTOs;
    }


}
