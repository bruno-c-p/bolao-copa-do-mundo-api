package live.bolaocopadomundo.api.dto.group;

import live.bolaocopadomundo.api.dto.team.TeamGroupDTO;
import live.bolaocopadomundo.api.entities.Group;
import live.bolaocopadomundo.api.entities.Team;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class GroupOutputDTO extends GroupDTO {

    private Long id;

    private Set<TeamGroupDTO> teams = new HashSet<>();

    public GroupOutputDTO() {
    }

    public GroupOutputDTO(Group entity) {
        super(entity);
        this.id = entity.getId();
        entity.getTeams().forEach(team -> this.teams.add(new TeamGroupDTO(team)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TeamGroupDTO> getTeams() {
        return teams;
    }
}
