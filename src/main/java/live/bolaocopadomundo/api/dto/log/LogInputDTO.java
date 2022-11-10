package live.bolaocopadomundo.api.dto.log;

import live.bolaocopadomundo.api.entities.Log;

public class LogInputDTO extends LogDTO {

    private Long user;
    private String action;

    public LogInputDTO() {
    }

    public LogInputDTO(Long user, String action) {
        this.user = user;
        this.action = action;
    }

    public LogInputDTO(Log entity) {
        this.user = entity.getUser().getId();
        this.action = String.valueOf(entity.getAction());
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
