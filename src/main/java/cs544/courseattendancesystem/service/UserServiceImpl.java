package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.User;
import cs544.courseattendancesystem.exception.NotFoundException;
import cs544.courseattendancesystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByUserName(String username) throws NotFoundException {
        return userRepository.findByUserName(username).orElseThrow(() -> new NotFoundException(String.format("User with user name %s is not found", username)));
    }
}
