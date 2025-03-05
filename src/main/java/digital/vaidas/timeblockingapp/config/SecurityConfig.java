package digital.vaidas.timeblockingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OpaqueTokenIntrospector introspector) throws Exception {
        http
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
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector() {
        return new OpaqueTokenIntrospector() {
            private final RestTemplate restTemplate = new RestTemplate();

            @Override
            public OAuth2AuthenticatedPrincipal introspect(String token) {
                String uri = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + token;
                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Map<String, Object>>() {}
                );
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new OAuth2IntrospectionException("Invalid token");
                }
                Map<String, Object> attributes = response.getBody();
                if (attributes.containsKey("exp")) {
                    attributes.put("exp", Instant.ofEpochSecond(Long.parseLong((String) attributes.get("exp"))));
                }
                return new DefaultOAuth2AuthenticatedPrincipal(
                        (String) attributes.get("sub"),
                        attributes,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
            }
        };
    }
}