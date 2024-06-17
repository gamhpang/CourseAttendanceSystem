package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Session;
import cs544.courseattendancesystem.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SessionServiceImplTest {

    @TestConfiguration
    static class SessionServiceImplTestContextConfiguration {
        @Bean
        public SessionServiceImpl sessionService() {
            return new SessionServiceImpl();
        }
    }

    @Autowired
    private SessionService sessionService;

    @MockBean
    private SessionRepository sessionRepository;

    private Session session;

    @BeforeEach
    public void setUp() {
        session = new Session(LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), LocalTime.of(12, 30));
        session.setId(1L);
    }

    @Test
    public void testGetSession_Success() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Session foundSession = sessionService.getSession(1L);

        assertNotNull(foundSession);
        assertEquals(session.getId(), foundSession.getId());
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetSession_NotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        Session foundSession = sessionService.getSession(1L);

        assertNull(foundSession);
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testGenerateSessions() {
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 6, 2);

        List<Session> expectedSessions = Arrays.asList(
                new Session(LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), LocalTime.of(12, 30)),
                new Session(LocalDate.of(2023, 6, 1), LocalTime.of(13, 30), LocalTime.of(15, 30)),
                new Session(LocalDate.of(2023, 6, 2), LocalTime.of(10, 0), LocalTime.of(12, 30)),
                new Session(LocalDate.of(2023, 6, 2), LocalTime.of(13, 30), LocalTime.of(15, 30))
        );

        List<Session> generatedSessions = sessionService.generateSessions(startDate, endDate);

        assertNotNull(generatedSessions);
        assertEquals(expectedSessions.size(), generatedSessions.size());
        for (int i = 0; i < expectedSessions.size(); i++) {
            assertEquals(expectedSessions.get(i).getSessionDate(), generatedSessions.get(i).getSessionDate());
            assertEquals(expectedSessions.get(i).getStartTime(), generatedSessions.get(i).getStartTime());
            assertEquals(expectedSessions.get(i).getEndTime(), generatedSessions.get(i).getEndTime());
        }
    }
}

