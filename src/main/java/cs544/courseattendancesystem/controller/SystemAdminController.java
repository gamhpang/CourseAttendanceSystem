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

    @PostMapping("/locations")
    public ResponseEntity<?> createLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO respLocationDTO = locationService.createLocation(locationDTO.getName(), locationDTO.getCapacity(), locationDTO.getTypeId());
        return new ResponseEntity<>(respLocationDTO, HttpStatus.OK);
    }

    @GetMapping("/locations/{locationId}")
    public ResponseEntity<?> getLocation(@PathVariable("locationId") long locationId) {
        LocationDTO location = locationService.getLocation(locationId);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }


    @GetMapping("/locations")
    public ResponseEntity<?> getLocations() {
        Collection<LocationDTO> locations = locationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @PutMapping("/locations")
    public ResponseEntity<?> updateLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO location = locationService.updateLocation(locationDTO);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }


    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable("locationId") long locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
