package live.bolaocopadomundo.api.resources;

import live.bolaocopadomundo.api.dto.user.UserInsertDTO;
import live.bolaocopadomundo.api.dto.user.UserOutputDTO;
import live.bolaocopadomundo.api.dto.user.UserPasswordDTO;
import live.bolaocopadomundo.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserOutputDTO>> findAll() {
        List<UserOutputDTO> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<UserOutputDTO>> ranking(@RequestParam(required = false) Integer limit) {
        List<UserOutputDTO> list = userService.ranking(limit);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserOutputDTO> findById(@PathVariable Long id) {
        UserOutputDTO dto = userService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/exists/nickname/{nickname}")
    public ResponseEntity<Boolean> existsByNickname(@PathVariable String nickname) {
        Boolean exists = userService.existsByNickname(nickname);
        return ResponseEntity.ok().body(exists);
    }

    @GetMapping(value = "/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        Boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok().body(exists);
    }

    @PatchMapping(value = "/{id}/admin")
    public ResponseEntity<UserOutputDTO> makeAdmin(@PathVariable Long id) {
        UserOutputDTO dto = userService.makeAdmin(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid UserInsertDTO dto) {
        String token = userService.create(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(token);
    }

    @PatchMapping(value = "password-reset")
    public ResponseEntity<?> changePassword(@RequestBody UserPasswordDTO dto) {
        userService.changePassword(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
