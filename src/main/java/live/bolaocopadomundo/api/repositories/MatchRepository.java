package live.bolaocopadomundo.api.repositories;

import live.bolaocopadomundo.api.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
