package live.bolaocopadomundo.api.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class TokenDTO implements Serializable {

    @NotBlank(message = "Required field")
    private String token;

    @NotBlank(message = "Required field")
    private String type;

    public TokenDTO(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
