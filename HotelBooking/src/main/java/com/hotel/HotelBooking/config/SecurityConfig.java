package com.hotel.HotelBooking.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    @Lazy
    private AuthFailureHandlerImpl authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
       return new UserDetailsServiceImpl();
   }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(req -> req
                            .requestMatchers("/user/**").hasRole("USER") // Thêm /identity trước đường dẫn
                            .requestMatchers("/admin/**").hasRole("ADMIN") // Thêm /identity trước đường dẫn
                            .requestMatchers("/**").permitAll()  // Thêm /identity trước đường dẫn
                )
                .formLogin(form -> form
                            .loginPage("/signin")  // Thêm /identity trước đường dẫn loginPage
                            .loginProcessingUrl("/login")  // Thêm /identity trước đường dẫn loginProcessingUrl
                            .failureHandler(authenticationFailureHandler)
                            .successHandler(authenticationSuccessHandler)
                )
                .logout(logout -> logout.permitAll());
            return http.build();
        }
}
