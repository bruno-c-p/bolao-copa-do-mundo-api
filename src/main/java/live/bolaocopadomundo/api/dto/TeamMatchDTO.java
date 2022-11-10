package live.bolaocopadomundo.api.dto;

import live.bolaocopadomundo.api.entities.TeamMatch;
import live.bolaocopadomundo.api.entities.enums.TeamMatchType;

import java.io.Serializable;

public class TeamMatchDTO implements Serializable {
    private String team;
    private TeamMatchType type;

    public TeamMatchDTO() {
    }

    public TeamMatchDTO(TeamMatch teamMatch) {
        this.team = teamMatch.getTeam().getName();
        this.type = teamMatch.getType();
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public TeamMatchType getType() {
        return type;
    }

    public void setType(TeamMatchType type) {
        this.type = type;
    }
}
