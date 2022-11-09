package live.bolaocopadomundo.api.entities.pk;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TeamMatchPK implements Serializable {
    private Long teamId;
    private Long matchId;

    public TeamMatchPK() {
    }

    public TeamMatchPK(Long teamId, Long matchId) {
        this.teamId = teamId;
        this.matchId = matchId;
    }
}
