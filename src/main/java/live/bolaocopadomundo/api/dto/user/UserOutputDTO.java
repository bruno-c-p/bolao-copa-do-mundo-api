package live.bolaocopadomundo.api.dto.user;

import live.bolaocopadomundo.api.entities.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserOutputDTO implements Serializable {

    private Long id;
    private String nickname;
    private String email;
    private Integer points;

    private Set<String> roles = new HashSet<>();

    public UserOutputDTO() {
    }

    public UserOutputDTO(Long id, String nickname, String email, Integer points, Set<String> roles) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.points = points;
        this.roles = roles;
    }

    public UserOutputDTO(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.points = user.getPoints();
        user.getRoles().forEach(role -> this.roles.add(role.getAuthority()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
