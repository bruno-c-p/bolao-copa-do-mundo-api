package live.bolaocopadomundo.api.repositories;

import live.bolaocopadomundo.api.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String team);
}
