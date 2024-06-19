package cs544.courseattendancesystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs544.courseattendancesystem.service.LocationService;
import cs544.courseattendancesystem.service.dto.LocationDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@RunWith(MockitoJUnitRunner.class)
public class SystemAdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private SystemAdminController systemAdminController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(systemAdminController).build();
    }

    @Test
    public void testCreateLocation() throws Exception {
        // Prepare test data
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setName("Room 101");
        locationDTO.setCapacity(50);
        locationDTO.setTypeId(1L);

        LocationDTO respLocationDTO = new LocationDTO();
        respLocationDTO.setName("Room 101");
        respLocationDTO.setCapacity(50);
        respLocationDTO.setTypeId(1L);

        // Mock the service method
        Mockito.when(locationService.createLocation(locationDTO.getName(), locationDTO.getCapacity(), locationDTO.getTypeId())).thenReturn(respLocationDTO);

        // Perform the POST request and check the response
        mockMvc.perform(post("/sys-admin/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Room 101"))
                .andExpect(jsonPath("$.capacity").value(50))
                .andExpect(jsonPath("$.typeId").value(1L));
    }

    @Test
    public void testGetLocation() throws Exception {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(1L);
        locationDTO.setName("Test Location");
        locationDTO.setCapacity(50);
        locationDTO.setTypeId(1L);

        when(locationService.getLocation(1L)).thenReturn(locationDTO);

        mockMvc.perform(get("/sys-admin/locations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.locationId").value(1L))
                .andExpect(jsonPath("$.name").value("Test Location"))
                .andExpect(jsonPath("$.capacity").value(50))
                .andExpect(jsonPath("$.typeId").value(1L));

        verify(locationService, times(1)).getLocation(1L);
        verifyNoMoreInteractions(locationService);
    }

    @Test
    public void testGetAllLocations() throws Exception {
        // Mock data
        LocationDTO location1 = new LocationDTO();
        location1.setLocationId(1L);
        location1.setName("Location 1");
        location1.setCapacity(50);
        location1.setTypeId(1L);

        LocationDTO location2 = new LocationDTO();
        location2.setLocationId(2L);
        location2.setName("Location 2");
        location2.setCapacity(75);
        location2.setTypeId(2L);

        List<LocationDTO> locations = Arrays.asList(location1, location2);

        // Mock the service method
        when(locationService.getAllLocations()).thenReturn(locations);

        // Perform the request and verify
        mockMvc.perform(get("/sys-admin/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].locationId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Location 1"))
                .andExpect(jsonPath("$[0].capacity").value(50))
                .andExpect(jsonPath("$[0].typeId").value(1L))
                .andExpect(jsonPath("$[1].locationId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Location 2"))
                .andExpect(jsonPath("$[1].capacity").value(75))
                .andExpect(jsonPath("$[1].typeId").value(2L));

        // Verify that the service method was called
        verify(locationService, times(1)).getAllLocations();
        verifyNoMoreInteractions(locationService);
    }

    @Test
    public void testUpdateLocation() throws Exception {
        // Mock data
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(1L);
        locationDTO.setName("Updated Location");
        locationDTO.setCapacity(100);
        locationDTO.setTypeId(2L);

        // Mock the service method
        when(locationService.updateLocation(any(LocationDTO.class))).thenReturn(locationDTO);

        // Perform the request and verify
        mockMvc.perform(put("/sys-admin/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.locationId").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Location"))
                .andExpect(jsonPath("$.capacity").value(100))
                .andExpect(jsonPath("$.typeId").value(2L));

        // Verify that the service method was called
        verify(locationService, times(1)).updateLocation(any(LocationDTO.class));
        verifyNoMoreInteractions(locationService);
    }

    @Test
    public void testDeleteLocation() throws Exception {
        long locationId = 1L;

        // Perform the request and verify
        mockMvc.perform(delete("/sys-admin/locations/{locationId}", locationId))
                .andExpect(status().isOk());

        // Verify that the service method was called
        verify(locationService, times(1)).deleteLocation(locationId);
        verifyNoMoreInteractions(locationService);
    }

    private String asJsonString(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }


}
