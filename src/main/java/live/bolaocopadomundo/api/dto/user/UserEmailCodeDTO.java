package live.bolaocopadomundo.api.dto;

import javax.validation.constraints.Size;

public class UserEmailCodeDTO extends UserTokenDTO {

    @Size(min = 6, max = 6, message = "Code must have 6 characters")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
