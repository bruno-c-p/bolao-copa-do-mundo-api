package live.bolaocopadomundo.api.dto.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UserLoginDTO implements Serializable {

    @NotBlank(message = "Required field")
    private String email;

    @NotBlank(message = "Required field")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken toUsernamePasswordAuth() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
