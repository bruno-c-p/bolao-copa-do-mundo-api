package live.bolaocopadomundo.api.resources;

import live.bolaocopadomundo.api.dto.user.UserDTO;
import live.bolaocopadomundo.api.dto.user.UserInsertDTO;
import live.bolaocopadomundo.api.dto.user.UserPasswordDTO;
import live.bolaocopadomundo.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> list = userService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO dto = userService.findById(id);
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
