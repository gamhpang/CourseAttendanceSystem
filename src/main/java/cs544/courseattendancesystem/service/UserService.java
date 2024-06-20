package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.User;
import cs544.courseattendancesystem.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByUserName(String email) throws NotFoundException;
}
