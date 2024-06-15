package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.service.LocationService;
import cs544.courseattendancesystem.service.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/sys-admin")
public class SystemAdminController {

    @Autowired
    LocationService locationService;

    @PostMapping("/locations/create")
    public ResponseEntity<?> createLocation(@RequestParam(value = "locationName") String locationName,
                                            @RequestParam(value = "capacity") int capacity) {

        LocationDTO locationDTO = locationService.createLocation(locationName, capacity);
        return new ResponseEntity<LocationDTO>(locationDTO, HttpStatus.OK);
    }

    @GetMapping("/locations/{locationId}")
    public ResponseEntity<?> getLocation(@PathVariable("locationId") long locationId) {
        LocationDTO location = locationService.getLocation(locationId);
        return new ResponseEntity<LocationDTO>(location, HttpStatus.OK);
    }


    @GetMapping("/locations")
    public ResponseEntity<?> getCourses() {
        Collection<LocationDTO> locations = locationService.getAllLocations();
        return new ResponseEntity<Collection<LocationDTO>>(locations, HttpStatus.OK);
    }

    @PutMapping("/locations")
    public ResponseEntity<?> updateLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO location = locationService.updateLocation(locationDTO);
        return new ResponseEntity<LocationDTO>(location, HttpStatus.OK);
    }


    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable("locationId") long locationId) {
        try {
            locationService.deleteLocation(locationId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}