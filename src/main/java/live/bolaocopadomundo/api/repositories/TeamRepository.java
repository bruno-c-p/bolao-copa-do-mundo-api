package live.bolaocopadomundo.api.repositories;

import live.bolaocopadomundo.api.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
