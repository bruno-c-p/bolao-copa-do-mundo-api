package live.bolaocopadomundo.api.dto.group;

import live.bolaocopadomundo.api.entities.Group;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class GroupInputDTO implements Serializable {
    @Size(min = 1, max = 1, message = "Name must have 1 character")
    @NotBlank(message = "Required field")
    private String name;

    public GroupInputDTO() {
    }

    public GroupInputDTO(Group entity) {
        this.name = entity.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
