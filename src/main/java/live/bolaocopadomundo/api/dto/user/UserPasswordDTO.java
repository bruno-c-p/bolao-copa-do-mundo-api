package live.bolaocopadomundo.api.dto;

public class UserPasswordDTO extends UserTokenDTO {

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
