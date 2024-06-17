package cs544.courseattendancesystem.service;


import cs544.courseattendancesystem.domain.Location;
import cs544.courseattendancesystem.domain.LocationType;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.LocationRepository;
import cs544.courseattendancesystem.repository.LocationTypeRepository;
import cs544.courseattendancesystem.service.dto.LocationDTO;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationTypeRepository locationTypeRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateLocation() {
        // Mocking behavior for LocationTypeRepository
        LocationType mockLocationType = new LocationType();
        mockLocationType.setId(1L);
        when(locationTypeRepository.findById(1L)).thenReturn(Optional.of(mockLocationType));

        // Mocking behavior for LocationRepository
        when(locationRepository.save(any(Location.class))).thenAnswer(invocation -> {
            Location location = invocation.getArgument(0);
            location.setId(1L); // Simulating save operation
            return location;
        });

        LocationDTO locationDTO = locationService.createLocation("Test Location", 100, 1L);

        assertNotNull(locationDTO);
        assertEquals("Test Location", locationDTO.getName());
        assertEquals(100, locationDTO.getCapacity());
        assertEquals(1L, locationDTO.getTypeId());
    }

    @Test
    public void testUpdateLocation() {
        Location existingLocation = new Location("Existing Location", 50);
        existingLocation.setLocationType(new LocationType("Existing Location Type"));
        existingLocation.setId(1L);

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(1L);
        locationDTO.setName("Updated Location");
        locationDTO.setCapacity(75);

        when(locationRepository.findById(1L)).thenReturn(Optional.of(existingLocation));
        when(locationRepository.save(any(Location.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LocationDTO updatedLocationDTO = locationService.updateLocation(locationDTO);

        assertNotNull(updatedLocationDTO);
        assertEquals("Updated Location", updatedLocationDTO.getName());
        assertEquals(75, updatedLocationDTO.getCapacity());
    }


    @Test
    public void testGetAllLocations() {
        Location location1 = new Location("Location 1", 50);
        location1.setLocationType(new LocationType("Location Type1"));
        Location location2 = new Location("Location 2", 75);
        location2.setLocationType(new LocationType("Location Type2"));

        when(locationRepository.findAll()).thenReturn(Arrays.asList(location1, location2));

        List<LocationDTO> locationDTOs = locationService.getAllLocations();

        assertNotNull(locationDTOs);
        assertEquals(2, locationDTOs.size());
        assertEquals("Location 1", locationDTOs.get(0).getName());
        assertEquals(50, locationDTOs.get(0).getCapacity());
        assertEquals("Location 2", locationDTOs.get(1).getName());
        assertEquals(75, locationDTOs.get(1).getCapacity());
    }

    @Test
    public void testDeleteLocation() {
        Location existingLocation = new Location("Test Location", 100);
        existingLocation.setId(1L);

        when(locationRepository.findById(1L)).thenReturn(Optional.of(existingLocation));

        locationService.deleteLocation(1L);

        verify(locationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindLocationById_LocationNotFound() {
        // Simulate location not found scenario
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            locationService.getLocation(1L);
        });
    }

    @Test
    public void testFindLocationTypeById_LocationTypeNotFound() {
        // Simulate location type not found scenario
        when(locationTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            locationService.createLocation("Test Location", 100, 1L);
        });
    }


    @Test
    public void testGetLocation() {
        Location existingLocation = new Location("Test Location", 100);
        LocationType locationType = new LocationType("Test LocationType");
        existingLocation.setId(1L);
        existingLocation.setLocationType(locationType);

        when(locationRepository.findById(1L)).thenReturn(Optional.of(existingLocation));

        LocationDTO locationDTO = locationService.getLocation(1L);

        assertNotNull(locationDTO);
        assertEquals("Test Location", locationDTO.getName());
        assertEquals(100, locationDTO.getCapacity());
    }


}
