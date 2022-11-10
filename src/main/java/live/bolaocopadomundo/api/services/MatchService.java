package live.bolaocopadomundo.api.services;

import live.bolaocopadomundo.api.dto.match.MatchInputDTO;
import live.bolaocopadomundo.api.dto.match.MatchOutputDTO;
import live.bolaocopadomundo.api.entities.Group;
import live.bolaocopadomundo.api.entities.Match;
import live.bolaocopadomundo.api.entities.TeamMatch;
import live.bolaocopadomundo.api.repositories.GroupRepository;
import live.bolaocopadomundo.api.repositories.MatchRepository;
import live.bolaocopadomundo.api.repositories.TeamRepository;
import live.bolaocopadomundo.api.services.exceptions.DatabaseException;
import live.bolaocopadomundo.api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public Page<MatchOutputDTO> findAllPaged(Pageable pageable) {
        Page<Match> list = matchRepository.findAll(pageable);
        return list.map(MatchOutputDTO::new);
    }

    @Transactional(readOnly = true)
    public MatchOutputDTO findById(Long id) {
        Optional<Match> match = matchRepository.findById(id);
        Match entity = match.orElseThrow(() -> new ResourceNotFoundException("Match not found"));
        return new MatchOutputDTO(entity);
    }

    @Transactional
    public MatchOutputDTO insert(MatchInputDTO dto) {
        Match entity = new Match();
        copyDtoToEntity(dto, entity);
        entity = matchRepository.save(entity);
        return new MatchOutputDTO(entity);
    }

    @Transactional
    public MatchOutputDTO update(Long id, MatchInputDTO dto) {
        try {
            Match entity = matchRepository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = matchRepository.save(entity);
            return new MatchOutputDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            matchRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(MatchInputDTO dto, Match entity) {
        List<Group> groups = new ArrayList<>();

        entity.setDate(dto.getDate());
        entity.getTeams().clear();

        if (dto.getTeams().size() != 2) {
            throw new DatabaseException("Match must have 2 teams");
        }

        dto.getTeams().forEach(teamMatchDTO ->
                entity.getTeams().add(
                        new TeamMatch(
                                teamRepository.findByName(teamMatchDTO.getTeam())
                                        .orElseThrow(() -> new ResourceNotFoundException("Team not found")),
                                entity,
                                teamMatchDTO.getType())
                )
        );

        if (!isTeamGroupMatch(entity.getTeams())) {
            throw new DatabaseException("Teams must be in the same group");
        }
    }

    private boolean isTeamGroupMatch(Set<TeamMatch> teams) {
        String group = teams.stream().findFirst().get().getTeam().getGroup().getName();
        return teams.stream().allMatch(teamMatch -> teamMatch.getTeam().getGroup().getName().equals(group));
    }
}
