package cs544.courseattendancesystem.config;

import org.junit.jupiter.api.BeforeEach;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.context.SecurityContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public abstract class BaseTest {

    @BeforeEach

    public void setupSecurityContext() {

        UserDetails userDetails = new User("john", "password", Collections.emptyList());

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        securityContext.setAuthentication(auth);

        SecurityContextHolder.setContext(securityContext);

    }

}
