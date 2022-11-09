package live.bolaocopadomundo.api.dto.team;

import live.bolaocopadomundo.api.entities.Team;

public class TeamInputDTO extends TeamDTO {

    private String group;

    public TeamInputDTO() {
    }

    public TeamInputDTO(String name, String acronym, String flagUrl, String group) {
        super(name, acronym, flagUrl);
        this.group = group;
    }

    public TeamInputDTO(Team entity) {
        super(entity);
        this.group = entity.getGroup().getName();
    }

    public String getGroup() {
        return group;
    }
}
