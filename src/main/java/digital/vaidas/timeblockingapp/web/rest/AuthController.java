package digital.vaidas.timeblockingapp.web.rest;

import digital.vaidas.timeblockingapp.model.User;
import digital.vaidas.timeblockingapp.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/user")
    public User getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        logger.debug("Processing /user request with OAuth2User attributes: {}",
                oAuth2User != null ? oAuth2User.getAttributes() : "null");
        return authService.processOAuth2User(oAuth2User);
    }

    @GetMapping("/public/test")
    public String test() {
        return "Public endpoint works!";
    }
}