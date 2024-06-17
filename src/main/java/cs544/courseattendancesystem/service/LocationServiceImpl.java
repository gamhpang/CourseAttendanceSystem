package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Location;
import cs544.courseattendancesystem.domain.LocationType;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.LocationRepository;
import cs544.courseattendancesystem.repository.LocationTypeRepository;
import cs544.courseattendancesystem.service.adapter.LocationAdapter;
import cs544.courseattendancesystem.service.dto.LocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTypeRepository locationTypeRepository;


    @Override
    public LocationDTO createLocation(String name, int capacity, Long locationTypeId) {
        LocationType locationType = findLocationTypeById(locationTypeId);

        Location location = new Location(name, capacity);
        location.setLocationType(locationType);
        locationRepository.save(location);

        return LocationAdapter.getLocationDTOFromLocation(location);
    }

    @Override
    public LocationDTO updateLocation(LocationDTO locationDTO) {
        Location location = findLocationById(locationDTO.getLocationId());
        location.setName(locationDTO.getName());
        location.setCapacity(locationDTO.getCapacity());
        locationRepository.save(location);

        return LocationAdapter.getLocationDTOFromLocation(location);
    }

    @Override
    public LocationDTO getLocation(long locationId) {
        Location location = findLocationById(locationId);
        return LocationAdapter.getLocationDTOFromLocation(location);

    }

    @Override
    public Location getLocationById(long locationId) {
        return findLocationById(locationId);
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return LocationAdapter.getLocationDTOsFromLocations(locations);
    }

    @Override
    public void deleteLocation(long locationId) {
        Location location = findLocationById(locationId);
        locationRepository.deleteById(location.getId());
    }

    private LocationType findLocationTypeById(long locationTypeId) {
        return locationTypeRepository.findById(locationTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("LocationType with id " + locationTypeId + " not found"));

    }

    private Location findLocationById(long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id " + locationId));
    }


}
