package live.bolaocopadomundo.api.dto.log;

import live.bolaocopadomundo.api.entities.Log;
import live.bolaocopadomundo.api.entities.enums.Action;

import javax.validation.constraints.NotNull;

public class LogInputDTO extends LogDTO {

    @NotNull(message = "Required field")
    private Long user;

    @NotNull(message = "Required field")
    private Action action;

    public LogInputDTO() {
    }

    public LogInputDTO(Long user, Action action) {
        this.user = user;
        this.action = action;
    }

    public LogInputDTO(Log entity) {
        this.user = entity.getUser().getId();
        this.action = entity.getAction();
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
