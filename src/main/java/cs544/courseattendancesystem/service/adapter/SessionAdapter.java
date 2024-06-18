package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Session;
import cs544.courseattendancesystem.service.dto.SessionDTO;
import org.springframework.stereotype.Service;

public class SessionAdapter {

    public static Session getSessionFromSessionDTO(SessionDTO sessionDTO){
        Session session = new Session(sessionDTO.getSessionDate(),sessionDTO.getStartTime(),sessionDTO.getEndTime());
        return session;
    }

    public static SessionDTO getSessionDTOFromSession(Session session){
        SessionDTO sessionDTO = new SessionDTO(session.getId(),session.getSessionDate(),session.getStartTime(),session.getEndTime());
        return sessionDTO;
    }


}
