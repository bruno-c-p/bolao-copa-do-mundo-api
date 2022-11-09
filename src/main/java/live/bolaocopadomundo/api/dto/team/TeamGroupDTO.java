package live.bolaocopadomundo.api.dto.team;

import live.bolaocopadomundo.api.entities.Team;

import java.io.Serializable;

public class TeamGroupDTO implements Serializable {

    private Long id;
    private String name;

    public TeamGroupDTO() {
    }

    public TeamGroupDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TeamGroupDTO(Team entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
