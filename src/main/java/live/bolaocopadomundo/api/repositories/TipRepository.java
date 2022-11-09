package live.bolaocopadomundo.api.repositories;

import live.bolaocopadomundo.api.entities.Tip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipRepository extends JpaRepository<Tip, Long> {
}
