package live.bolaocopadomundo.api.resources;

import live.bolaocopadomundo.api.dto.TokenDTO;
import live.bolaocopadomundo.api.dto.UserEmailCodeDTO;
import live.bolaocopadomundo.api.dto.UserEmailDTO;
import live.bolaocopadomundo.api.dto.UserLoginDTO;
import live.bolaocopadomundo.api.services.security.AuthService;
import live.bolaocopadomundo.api.services.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDTO dto) {
        UsernamePasswordAuthenticationToken loginData = dto.toUsernamePasswordAuth();

        try {
            Authentication authentication = authenticationManager.authenticate(loginData);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException e) {
            return new ResponseEntity<Object>("Email ou senha inválidos", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
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
