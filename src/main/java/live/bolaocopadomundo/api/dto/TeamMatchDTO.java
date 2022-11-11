package live.bolaocopadomundo.api.dto;

import live.bolaocopadomundo.api.entities.TeamMatch;
import live.bolaocopadomundo.api.entities.enums.TeamMatchType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TeamMatchDTO implements Serializable {

    @NotBlank(message = "Required field")
    private String team;

    private String acronym;

    @NotNull(message = "Required field")
    private TeamMatchType type;

    private String flagUrl;

    public TeamMatchDTO() {
    }

    public TeamMatchDTO(TeamMatch teamMatch) {
        this.team = teamMatch.getTeam().getName();
        this.acronym = teamMatch.getTeam().getAcronym();
        this.type = teamMatch.getType();
        this.flagUrl = teamMatch.getTeam().getFlagUrl();
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

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
}
