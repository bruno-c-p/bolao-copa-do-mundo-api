package live.bolaocopadomundo.api.dto.match;

import live.bolaocopadomundo.api.entities.Match;
import live.bolaocopadomundo.api.entities.enums.Result;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class MatchFinishDTO implements Serializable {

    @NotNull(message = "Required field")
    private Result result;

    public MatchFinishDTO() {
    }

    public MatchFinishDTO(Result result) {
        this.result = result;
    }

    public MatchFinishDTO(Match match) {
        this.result = match.getResult();
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
