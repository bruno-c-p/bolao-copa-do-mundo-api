package live.bolaocopadomundo.api.dto.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import live.bolaocopadomundo.api.dto.TeamMatchDTO;
import live.bolaocopadomundo.api.entities.Match;
import live.bolaocopadomundo.api.entities.enums.Result;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MatchOutputDTO extends MatchDTO {

    private Long id;
    private Set<TeamMatchDTO> teams = new LinkedHashSet<>();
    private Result result;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    public MatchOutputDTO() {
    }

    public MatchOutputDTO(Long id, Set<TeamMatchDTO> teams, Result result, LocalDateTime date) {
        this.id = id;
        this.teams = teams;
        this.result = result;
        this.date = date;
    }

    public MatchOutputDTO(Match entity) {
        id = entity.getId();
        teams = entity.getTeams().stream().map(TeamMatchDTO::new).collect(Collectors.toSet());
        result = entity.getResult();
        date = entity.getDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TeamMatchDTO> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamMatchDTO> teams) {
        this.teams = teams;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
