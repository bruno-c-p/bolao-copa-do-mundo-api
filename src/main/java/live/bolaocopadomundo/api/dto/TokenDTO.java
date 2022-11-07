package live.bolaocopadomundo.api.dto;

import java.io.Serializable;

public class TokenDTO implements Serializable {

    private String token;
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
