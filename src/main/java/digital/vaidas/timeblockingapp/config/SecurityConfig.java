//package digital.vaidas.timeblockingapp.config;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Configuration
//public class SecurityConfig {
//
//    private final JwtRequestFilter jwtRequestFilter;
//
//    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/public/**", "/login", "/oauth2/**").permitAll()
//                        .requestMatchers("/tasks/**", "/folders/**").authenticated()
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/oauth2/authorization/google")
//                        .defaultSuccessUrl("/user", true)
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(exc -> exc
//                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                )
//                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
//                .build();
//    }
//
//    @Component
//    public static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//        private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
//
//        @Override
//        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
//                throws IOException {
//            log.warn("Unauthorized request: {}", authException.getMessage());
//            response.setContentType("application/json");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}");
//        }
//    }
//}