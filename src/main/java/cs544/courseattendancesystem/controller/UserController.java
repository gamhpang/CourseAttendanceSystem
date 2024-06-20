package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.config.JWTMgmtUtilityService;
import cs544.courseattendancesystem.exception.NotFoundException;
import cs544.courseattendancesystem.exception.UserInvalidCredentialException;
import cs544.courseattendancesystem.service.UserService;
import cs544.courseattendancesystem.service.dto.UserRequestDTO;
import cs544.courseattendancesystem.service.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private JWTMgmtUtilityService jwtMgmtUtilityService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO) throws NotFoundException, UserInvalidCredentialException {
        UserResponseDTO userResponseDTO = null;
        try {
            var username = userRequestDTO.getUsername();
            var password = userRequestDTO.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            password)
            );
            var jwtToken = jwtMgmtUtilityService.generateToken(username);
            var user = userService.getUserByUserName(username);
            if (user != null) {
                userResponseDTO = new UserResponseDTO();
                userResponseDTO.setUsername(user.getUsername());
                userResponseDTO.setToken(jwtToken);
            } else {
                throw new UserInvalidCredentialException("Invalid Credential");
            }
        } catch (Exception ex) {
            System.out.println(String.format("User Authentication Exception is: %s", ex));
            throw new UserInvalidCredentialException(ex.getMessage());
        }
        return ResponseEntity.ok(userResponseDTO);
    }
}
