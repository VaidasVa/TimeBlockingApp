//package digital.vaidas.timeblockingapp.web.rest;
//
//import digital.vaidas.timeblockingapp.config.JwtTokenUtil;
//import digital.vaidas.timeblockingapp.model.User;
//import digital.vaidas.timeblockingapp.service.AuthService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class AuthController {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//    private final AuthService authService;
//    private final JwtTokenUtil jwtTokenUtil;
//
//    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil) {
//        this.authService = authService;
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
//    @GetMapping("/user")
//    public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
//        logger.debug("Processing /user request with OAuth2User attributes: {}",
//                oAuth2User != null ? oAuth2User.getAttributes() : "null");
//
//        User user = authService.processOAuth2User(oAuth2User);
//
//        // Generate JWT token
//        String token = jwtTokenUtil.generateToken(user.getId());
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("user", user);
//        response.put("token", token);
//
//        return ResponseEntity.ok(response);
//    }
//}