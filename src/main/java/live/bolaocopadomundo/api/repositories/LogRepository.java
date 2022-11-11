package live.bolaocopadomundo.api.repositories;

import live.bolaocopadomundo.api.entities.Log;
import live.bolaocopadomundo.api.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LogRepository extends JpaRepository<Log, Long>, QuerydslPredicateExecutor<Log> {
}
