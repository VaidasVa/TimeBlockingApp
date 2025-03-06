package digital.vaidas.timeblockingapp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OpaqueTokenIntrospector introspector) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/tasks/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .defaultSuccessUrl("/user", true)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaque -> opaque.introspector(introspector))
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // Pridėta
                )
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
                .build();
    }

    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(RestTemplate restTemplate) {
        return token -> {
            String uri = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + token;
            try {
                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                        uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
                );
                Map<String, Object> attributes = Optional.ofNullable(response.getBody()).orElseThrow();

                attributes.computeIfPresent("exp", (k, v) -> Instant.ofEpochSecond(Long.parseLong((String) v)));

                return new DefaultOAuth2AuthenticatedPrincipal(
                        (String) attributes.get("sub"), attributes, List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
            } catch (Exception e) {
                throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Invalid or expired token", null));
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Component
    public static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
                throws IOException {
            log.warn("Unauthorized request: {}", authException.getMessage());
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}");
        }
    }
}