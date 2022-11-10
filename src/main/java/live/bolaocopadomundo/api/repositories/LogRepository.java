package live.bolaocopadomundo.api.repositories;

import live.bolaocopadomundo.api.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
