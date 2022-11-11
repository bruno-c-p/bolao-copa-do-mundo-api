package live.bolaocopadomundo.api.dto.user;

import javax.validation.constraints.NotBlank;

public class UserPasswordDTO extends UserTokenDTO {

    @NotBlank(message = "Required field")
    private String password;

    public UserPasswordDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
