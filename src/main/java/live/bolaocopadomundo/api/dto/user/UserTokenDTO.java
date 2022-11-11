package live.bolaocopadomundo.api.dto.user;

import javax.validation.constraints.NotBlank;

public class UserTokenDTO {

    @NotBlank(message = "Required field")
    private String token;

    public UserTokenDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
