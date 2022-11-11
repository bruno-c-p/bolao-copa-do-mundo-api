package live.bolaocopadomundo.api.resources;

import live.bolaocopadomundo.api.dto.log.LogInputDTO;
import live.bolaocopadomundo.api.dto.log.LogOutputDTO;
import live.bolaocopadomundo.api.services.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogResource {

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<List<LogOutputDTO>> findAll() {
        List<LogOutputDTO> list = logService.findAllPaged();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LogOutputDTO> findById(@PathVariable Long id) {
        LogOutputDTO dto = logService.findById(id);
        return ResponseEntity.ok().body(dto);
    }
}
