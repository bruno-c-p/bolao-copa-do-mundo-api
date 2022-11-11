package live.bolaocopadomundo.api.services;

import live.bolaocopadomundo.api.dto.tip.TipInputDTO;
import live.bolaocopadomundo.api.dto.tip.TipOutputDTO;
import live.bolaocopadomundo.api.entities.Match;
import live.bolaocopadomundo.api.entities.Tip;
import live.bolaocopadomundo.api.entities.User;
import live.bolaocopadomundo.api.repositories.MatchRepository;
import live.bolaocopadomundo.api.repositories.TipRepository;
import live.bolaocopadomundo.api.repositories.UserRepository;
import live.bolaocopadomundo.api.services.exceptions.DatabaseException;
import live.bolaocopadomundo.api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TipService {

    @Autowired
    private TipRepository tipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Transactional(readOnly = true)
    public Page<TipOutputDTO> findAllPaged(Pageable pageable) {
        Page<Tip> list = tipRepository.findAll(pageable);
        return list.map(TipOutputDTO::new);
    }

    @Transactional(readOnly = true)
    public TipOutputDTO findById(Long id) {
        Optional<Tip> tip = tipRepository.findById(id);
        Tip entity = tip.orElseThrow(() -> new ResourceNotFoundException("Tip not found"));
        return new TipOutputDTO(entity);
    }

    @Transactional
    public TipOutputDTO insert(TipInputDTO dto) {
        Tip entity = new Tip();
        copyDtoToEntity(dto, entity);
        entity = tipRepository.save(entity);
        return new TipOutputDTO(entity);
    }

    public void delete(Long id) {
        try {
            tipRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(TipInputDTO dto, Tip entity) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Match match = matchRepository.getOne(dto.getMatch());

        if (match.getDate().isBefore(LocalDateTime.now())) {
            throw new DatabaseException("Match already started");
        }

        Optional<Tip> tip = tipRepository.findByMatchAndUser(match, user);
        if (tip.isPresent()) {
            throw new DatabaseException("User already tipped this match");
        }

        entity.setMatch(match);
        entity.setResult(dto.getResult());
        entity.setUser(user);
    }
}
