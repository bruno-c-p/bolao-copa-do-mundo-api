package live.bolaocopadomundo.api.services;

import live.bolaocopadomundo.api.dto.log.LogInputDTO;
import live.bolaocopadomundo.api.dto.log.LogOutputDTO;
import live.bolaocopadomundo.api.entities.Log;
import live.bolaocopadomundo.api.entities.enums.Action;
import live.bolaocopadomundo.api.repositories.LogRepository;
import live.bolaocopadomundo.api.repositories.UserRepository;
import live.bolaocopadomundo.api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<LogOutputDTO> findAllPaged(Pageable pageable) {
        Page<Log> list = logRepository.findAll(pageable);
        return list.map(LogOutputDTO::new);
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
            entity.setAction(Action.valueOf(dto.getAction()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid action");
        }

        entity = logRepository.save(entity);
        return new LogOutputDTO(entity);
    }
}
