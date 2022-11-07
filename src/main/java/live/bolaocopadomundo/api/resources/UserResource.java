package live.bolaocopadomundo.api.resources;

import live.bolaocopadomundo.api.dto.UserDTO;
import live.bolaocopadomundo.api.dto.UserInsertDTO;
import live.bolaocopadomundo.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserInsertDTO dto) {
        UserDTO user = userService.create(dto);
        return ResponseEntity.ok().body(user);
    }
}
