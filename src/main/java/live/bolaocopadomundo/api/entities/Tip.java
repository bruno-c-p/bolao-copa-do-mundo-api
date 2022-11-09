package live.bolaocopadomundo.api.entities;


import live.bolaocopadomundo.api.entities.enums.Result;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_tip")
public class Tip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Match match;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Result result;

    public Tip() {
    }

    public Tip(Long id, Match match, User user, Result result) {
        this.id = id;
        this.match = match;
        this.user = user;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tip other = (Tip) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
