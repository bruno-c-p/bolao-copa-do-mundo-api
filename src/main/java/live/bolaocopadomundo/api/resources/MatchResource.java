package live.bolaocopadomundo.api.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import live.bolaocopadomundo.api.dto.match.*;
import live.bolaocopadomundo.api.dto.match.MatchInputDTO;
import live.bolaocopadomundo.api.dto.match.MatchOutputDTO;
import live.bolaocopadomundo.api.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchResource {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchOutputDTO>> findAll(
            @RequestParam(required = false)
            @JsonFormat(pattern = "yyyy-MM-dd")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @JsonDeserialize(using = LocalDateDeserializer.class)
            @JsonSerialize(using = LocalDateSerializer.class) LocalDate date) {
        List<MatchOutputDTO> list = matchService.findAll(date);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MatchOutputDTO> findById(@PathVariable Long id) {
        MatchOutputDTO dto = matchService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<MatchOutputDTO> insert(@RequestBody @Valid MatchInputDTO dto) {
        MatchOutputDTO newDto = matchService.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDto);
    }

    @PatchMapping(value = "/{id}/finish")
    public ResponseEntity<MatchOutputDTO> finishMatch(@PathVariable Long id, @RequestBody MatchFinishDTO dto) {
        MatchOutputDTO newDto = matchService.finishMatch(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MatchOutputDTO> update(@PathVariable Long id, @RequestBody @Valid MatchInputDTO dto) {
        MatchOutputDTO newDto = matchService.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
