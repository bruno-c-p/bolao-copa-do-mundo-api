package live.bolaocopadomundo.api.services.security;

import live.bolaocopadomundo.api.dto.user.UserEmailCodeDTO;
import live.bolaocopadomundo.api.dto.user.UserEmailDTO;
import live.bolaocopadomundo.api.entities.User;
import live.bolaocopadomundo.api.entities.enums.Status;
import live.bolaocopadomundo.api.repositories.UserRepository;
import live.bolaocopadomundo.api.services.email.EmailService;
import live.bolaocopadomundo.api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    public boolean isEmailValidated(UserEmailCodeDTO dto) {
        String userEmail = tokenService.getUserEmail(dto.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getActivationCode().equals(dto.getCode())) {
            user.setActivationCode(null);
            user.setStatus(Status.CONFIRMED);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public void resendActivationCode(UserEmailCodeDTO dto) {
        String userEmail = tokenService.getUserEmail(dto.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        emailService.sendConfirmationEmail(user);
    }

    public void sendPasswordReset(UserEmailDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String token = tokenService.generateEmailToken(user);
        emailService.sendPasswordResetEmail(user, token);
    }
}
