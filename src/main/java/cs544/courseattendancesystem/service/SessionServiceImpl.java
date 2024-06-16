package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Session;
import cs544.courseattendancesystem.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService{
    @Autowired
    SessionRepository sessionRepository;

    @Override
    public Session getSession(long sessionId) {
        return sessionRepository.findById(sessionId).orElse(null);
    }
}
