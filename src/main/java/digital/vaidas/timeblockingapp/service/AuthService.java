package digital.vaidas.timeblockingapp.service;

import digital.vaidas.timeblockingapp.model.User;
import digital.vaidas.timeblockingapp.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User processOAuth2User(OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new IllegalStateException("OAuth2User is null");
        }

        String id = oAuth2User.getAttribute("sub");
        if (id == null) {
            throw new IllegalArgumentException("Missing 'sub' attribute in OAuth2User");
        }

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Optional<User> existingUser = userRepository.findById(id);
        User user = existingUser.orElse(new User());
        user.setId(id);
        user.setEmail(email != null ? email : "unknown@example.com");
        user.setName(name != null ? name : "Unknown");

        return userRepository.save(user);
    }
}