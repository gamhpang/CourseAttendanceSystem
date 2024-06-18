package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Location;
import cs544.courseattendancesystem.domain.LocationType;
import cs544.courseattendancesystem.service.dto.LocationDTO;
import cs544.courseattendancesystem.service.dto.LocationTypeDTO;

import java.util.ArrayList;
import java.util.List;

public class LocationTypeAdapter {
    public static LocationTypeDTO getLocationDTOFromLocation(LocationType locationType) {
        LocationTypeDTO locationTypeDTO = new LocationTypeDTO();
        locationTypeDTO.setId(locationType.getId());
        locationTypeDTO.setType(locationType.getType());
        return locationTypeDTO;
    }

    public static List<LocationTypeDTO> getLocationDTOsFromLocations(List<LocationType> locationTypes) {
        List<LocationTypeDTO> locationTypeDTOs = new ArrayList<>();
        for (LocationType locationType : locationTypes) {
            locationTypeDTOs.add(getLocationDTOFromLocation(locationType));
        }
        return locationTypeDTOs;
    }
}
