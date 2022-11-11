package live.bolaocopadomundo.api.repositories;

import live.bolaocopadomundo.api.entities.Match;
import live.bolaocopadomundo.api.entities.Tip;
import live.bolaocopadomundo.api.entities.User;
import live.bolaocopadomundo.api.entities.enums.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TipRepository extends JpaRepository<Tip, Long> {
    List<Tip> findAllByMatchIdAndResultEquals(Long matchId, Result result);

    Optional<Tip> findByMatchAndUser(Match match, User user);
}
