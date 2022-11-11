package live.bolaocopadomundo.api.dto.tip;

import live.bolaocopadomundo.api.entities.Tip;
import live.bolaocopadomundo.api.entities.enums.Result;

public class TipOutputDTO extends TipDTO {

    private Long id;
    private TipMatchOutputDTO match;
    private TipUserOutputDTO user;
    private Result result;

    public TipOutputDTO() {
    }

    public TipOutputDTO(Long id, TipMatchOutputDTO match, TipUserOutputDTO user, Result result) {
        this.id = id;
        this.match = match;
        this.user = user;
        this.result = result;
    }

    public TipOutputDTO(Tip entity) {
        id = entity.getId();
        match = new TipMatchOutputDTO(entity.getMatch());
        user = new TipUserOutputDTO(entity.getUser());
        result = entity.getResult();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipMatchOutputDTO getMatch() {
        return match;
    }

    public void setMatch(TipMatchOutputDTO match) {
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
