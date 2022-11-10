package live.bolaocopadomundo.api.dto.log;

import live.bolaocopadomundo.api.entities.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogOutputDTO extends LogDTO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Long id;
    private String user;
    private String date;
    private String action;

    public LogOutputDTO() {
    }

    public LogOutputDTO(Long id, String user, LocalDateTime date, String action) {
        this.id = id;
        this.user = user;
        this.date = date.format(formatter);
        this.action = action;
    }

    public LogOutputDTO(Log entity) {
        this.id = entity.getId();
        this.user = entity.getUser().getUsername();
        this.date = entity.getDate().format(formatter);
        this.action = String.valueOf(entity.getAction());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
