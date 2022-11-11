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
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MatchInputDTO extends MatchDTO {

    private Set<TeamMatchDTO> teams = new LinkedHashSet<>();

    @FutureOrPresent(message = "Date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = "Required field")
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
