package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Session;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface SessionService{

    public Session getSession(long sessionId);

    public List<Session> generateSessions(LocalDate startDate, LocalDate endDate);
}
