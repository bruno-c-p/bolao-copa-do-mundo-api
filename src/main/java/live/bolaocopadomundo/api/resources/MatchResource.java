package live.bolaocopadomundo.api.resources;

import live.bolaocopadomundo.api.dto.match.MatchInputDTO;
import live.bolaocopadomundo.api.dto.match.MatchOutputDTO;
import live.bolaocopadomundo.api.dto.match.MatchInputDTO;
import live.bolaocopadomundo.api.dto.match.MatchOutputDTO;
import live.bolaocopadomundo.api.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/matches")
public class MatchResource {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<Page<MatchOutputDTO>> findAll(Pageable pageable) {
        Page<MatchOutputDTO> list = matchService.findAllPaged(pageable);
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
