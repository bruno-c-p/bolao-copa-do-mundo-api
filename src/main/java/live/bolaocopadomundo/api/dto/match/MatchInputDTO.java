package live.bolaocopadomundo.api.dto.match;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import live.bolaocopadomundo.api.dto.TeamMatchDTO;
import live.bolaocopadomundo.api.entities.Match;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MatchInputDTO extends MatchDTO {

    private Set<TeamMatchDTO> teams = new LinkedHashSet<>();

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    public MatchInputDTO() {
    }

    public MatchInputDTO(Set<TeamMatchDTO> teams, LocalDateTime date) {
        this.teams = teams;
        this.date = date;
    }

    public MatchInputDTO(Match match) {
        teams = match.getTeams().stream().map(TeamMatchDTO::new).collect(Collectors.toSet());
        date = match.getDate();
    }

    public Set<TeamMatchDTO> getTeams() {
        return teams;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
