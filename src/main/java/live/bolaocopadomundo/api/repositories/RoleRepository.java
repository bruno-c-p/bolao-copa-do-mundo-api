package live.bolaocopadomundo.api.repositories;

import live.bolaocopadomundo.api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String role);
}
