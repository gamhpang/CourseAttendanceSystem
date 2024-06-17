package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Session;
import cs544.courseattendancesystem.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService{
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Session getSession(long sessionId) {
        return sessionRepository.findById(sessionId).orElse(null);
    }

    @Override
    public List<Session> generateSessions(LocalDate startDate, LocalDate endDate){
        List<Session> sessions = new ArrayList<>();
        LocalTime morningStartTime = LocalTime.of(10,0);
        LocalTime morningEndTime = LocalTime.of(12,30);
        LocalTime afternoonStartTime = LocalTime.of(13,30);
        LocalTime afternoonEndTime = LocalTime.of(15,30);

        for(LocalDate date = startDate; !date.isAfter(endDate); date=date.plusDays(1)){
            if(date.getDayOfWeek() != DayOfWeek.SUNDAY){
                //// Add morning session for all days exceptSunday
                sessions.add(new Session(date,morningStartTime,morningEndTime));

                // Add afternoon session for all days except Saturday and Sunday
                if(date.getDayOfWeek() != DayOfWeek.SATURDAY){
                    sessions.add(new Session(date,afternoonStartTime,afternoonEndTime));
                }
            }
        }
        return sessions;
    }

}
