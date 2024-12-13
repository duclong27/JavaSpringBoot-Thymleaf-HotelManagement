package com.hotel.HotelBooking.config;

import com.hotel.HotelBooking.entities.User;
import com.hotel.HotelBooking.repositories.UserRepository;
import com.hotel.HotelBooking.service.UserService;
import com.hotel.HotelBooking.util.AppConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;


import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;


@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("username");
        User user = userRepository.findByEmail(email);
        if (user.getIsEnable()) {
            if (user.getAccountNonLocked()) {
                if (user.getFailedAttempt() < AppConstant.ATTEMPT_TIME) {
                    userService.increaseFailedAttempt(user);
                } else {
                    userService.userAccountLock(user);
                    exception = new LockedException("Your account is locked !! failed attempt 3");
                }
            } else {
                if (userService.unlockAccountTimeExpired(user)) {
                    exception = new LockedException("Your account is unlocked !! Please try to login");
                } else {
                    exception = new LockedException("your account is Locked !! Please try after sometimes");
                }
            }
        } else {
            exception = new LockedException("your account is inactive");
        }
        super.setDefaultFailureUrl("/signin?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
