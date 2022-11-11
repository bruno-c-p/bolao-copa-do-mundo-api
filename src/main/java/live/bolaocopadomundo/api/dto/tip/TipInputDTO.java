package live.bolaocopadomundo.api.dto.tip;

import live.bolaocopadomundo.api.entities.Tip;
import live.bolaocopadomundo.api.entities.enums.Result;

import javax.validation.constraints.NotNull;

public class TipInputDTO extends TipDTO {

    @NotNull(message = "Required field")
    private Long match;

    @NotNull(message = "Required field")
    private TipUserOutputDTO user;

    @NotNull(message = "Required field")
    private Result result;

    public TipInputDTO() {
    }

    public TipInputDTO(Long match, TipUserOutputDTO user, Result result) {
        this.match = match;
        this.user = user;
        this.result = result;
    }

    public TipInputDTO(Tip entity) {
        match = entity.getMatch().getId();
        user = new TipUserOutputDTO(entity.getUser());
        result = entity.getResult();
    }

    public Long getMatch() {
        return match;
    }

    public void setMatch(Long match) {
        this.match = match;
    }

    public TipUserOutputDTO getUser() {
        return user;
    }

    public void setUser(TipUserOutputDTO user) {
        this.user = user;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
