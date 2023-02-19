package live.bolaocopadomundo.api.services;

import com.querydsl.core.BooleanBuilder;
import live.bolaocopadomundo.api.dto.log.LogInputDTO;
import live.bolaocopadomundo.api.dto.log.LogOutputDTO;
import live.bolaocopadomundo.api.entities.Log;
import live.bolaocopadomundo.api.entities.QLog;
import live.bolaocopadomundo.api.repositories.LogRepository;
import live.bolaocopadomundo.api.repositories.UserRepository;
import live.bolaocopadomundo.api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<LogOutputDTO> findAllPaged(LocalDate date) {
        BooleanBuilder builder = new BooleanBuilder();

        if (date != null) {
            builder.and(QLog.log.date.between(date.atStartOfDay(), date.atTime(23, 59, 59)));
        }

        List<Log> list = (List<Log>) logRepository.findAll(builder);
        return list.stream().map(LogOutputDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LogOutputDTO findById(Long id) {
        Optional<Log> group = logRepository.findById(id);
        Log entity = group.orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        return new LogOutputDTO(entity);
    }

    @Transactional
    public LogOutputDTO insert(LogInputDTO dto) {
        Log entity = new Log();
        entity.setUser(userRepository.getOne(dto.getUser()));

        try {
            entity.setAction(dto.getAction());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid action");
        }

        entity = logRepository.save(entity);
        return new LogOutputDTO(entity);
    }
}
