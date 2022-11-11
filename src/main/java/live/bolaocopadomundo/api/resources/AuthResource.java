package live.bolaocopadomundo.api.resources;

import live.bolaocopadomundo.api.config.security.JwtAuthenticationProvider;
import live.bolaocopadomundo.api.dto.TokenDTO;
import live.bolaocopadomundo.api.dto.log.LogInputDTO;
import live.bolaocopadomundo.api.dto.user.UserEmailCodeDTO;
import live.bolaocopadomundo.api.dto.user.UserEmailDTO;
import live.bolaocopadomundo.api.dto.user.UserLoginDTO;
import live.bolaocopadomundo.api.entities.enums.Action;
import live.bolaocopadomundo.api.services.LogService;
import live.bolaocopadomundo.api.services.UserService;
import live.bolaocopadomundo.api.services.security.AuthService;
import live.bolaocopadomundo.api.services.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JwtAuthenticationProvider authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDTO dto) {
        UsernamePasswordAuthenticationToken loginData = dto.toUsernamePasswordAuth();
        try {
            Authentication authentication = authenticationManager.authenticate(loginData);
            String token = tokenService.generateToken(authentication);
            logService.insert(new LogInputDTO(userService.findByEmail((String) loginData.getPrincipal()).getId(), Action.LOGIN));
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "email-password-reset")
    public ResponseEntity<?> emailPasswordReset(@RequestBody @Valid UserEmailDTO dto) {
        authService.sendPasswordReset(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/activation-code")
    public ResponseEntity<?> activationCode(@RequestBody @Valid UserEmailCodeDTO dto) {
        boolean notValid = !(authService.isEmailValidated(dto));

        if (notValid) {
            return new ResponseEntity<Object>("Código de ativação inválido", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/resend-activation-code")
    public ResponseEntity<?> resendActivationCode(@RequestBody @Valid UserEmailCodeDTO dto) {
        authService.resendActivationCode(dto);
        return ResponseEntity.ok().build();
    }
}
