package live.bolaocopadomundo.api.dto.team;

import live.bolaocopadomundo.api.entities.Team;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class TeamDTO implements Serializable {

    @NotBlank(message = "Required field")
    @Size(max = 30, message = "Name must have at most 30 characters")
    private String name;

    @NotBlank(message = "Required field")
    @Size(min = 3, max = 3)
    private String acronym;

    @Size(max = 255, message = "Flag url must have at most 255 characters")
    private String flagUrl;

    public TeamDTO() {
    }

    public TeamDTO(String name, String acronym, String flagUrl) {
        this.name = name;
        this.acronym = acronym;
        this.flagUrl = flagUrl;
    }

    public TeamDTO(Team team) {
        this.name = team.getName();
        this.acronym = team.getAcronym();
        this.flagUrl = team.getFlagUrl();
    }

    public TeamDTO(Long id, String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }
}
