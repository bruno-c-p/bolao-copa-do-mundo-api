package live.bolaocopadomundo.api.dto.tip;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import live.bolaocopadomundo.api.entities.Match;
import live.bolaocopadomundo.api.entities.enums.Result;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TipMatchOutputDTO implements Serializable {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    private Result result;

    public TipMatchOutputDTO() {
    }

    public TipMatchOutputDTO(Long id, LocalDateTime date, Result result) {
        this.id = id;
        this.date = date;
        this.result = result;
    }

    public TipMatchOutputDTO(Match match) {
        this.id = match.getId();
        this.date = match.getDate();
        this.result = match.getResult();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
