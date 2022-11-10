package live.bolaocopadomundo.api.entities;

import live.bolaocopadomundo.api.entities.enums.TeamMatchType;
import live.bolaocopadomundo.api.entities.pk.TeamMatchPK;

import javax.persistence.*;

@Entity
@Table(name = "tb_team_match")
public class TeamMatch {

    @EmbeddedId
    private TeamMatchPK id = new TeamMatchPK();

    @MapsId("teamId")
    @ManyToOne
    private Team team;

    @MapsId("matchId")
    @ManyToOne
    private Match match;

    private TeamMatchType type;

    public TeamMatch() {
    }

    public TeamMatch(Team team, Match match, TeamMatchType type) {
        this.team = team;
        this.match = match;
        this.type = type;
    }

    public TeamMatchPK getId() {
        return id;
    }

    public void setId(TeamMatchPK id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public TeamMatchType getType() {
        return type;
    }

    public void setType(TeamMatchType type) {
        this.type = type;
    }
}
