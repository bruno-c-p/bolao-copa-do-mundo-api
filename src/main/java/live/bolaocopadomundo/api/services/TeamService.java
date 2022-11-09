package live.bolaocopadomundo.api.services;

import live.bolaocopadomundo.api.dto.team.TeamInputDTO;
import live.bolaocopadomundo.api.dto.team.TeamOutputDTO;
import live.bolaocopadomundo.api.entities.Group;
import live.bolaocopadomundo.api.entities.Team;
import live.bolaocopadomundo.api.repositories.GroupRepository;
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
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public Page<TeamOutputDTO> findAllPaged(Pageable pageable) {
        Page<Team> list = teamRepository.findAll(pageable);
        return list.map(TeamOutputDTO::new);
    }

    @Transactional(readOnly = true)
    public TeamOutputDTO findById(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        Team entity = team.orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        return new TeamOutputDTO(entity);
    }

    @Transactional
    public TeamOutputDTO insert(TeamInputDTO dto) {
        Team entity = new Team();
        copyDtoToEntity(dto, entity);
        entity = teamRepository.save(entity);
        return new TeamOutputDTO(entity);
    }

    @Transactional
    public TeamOutputDTO update(Long id, TeamInputDTO dto) {
        try {
            Team entity = teamRepository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = teamRepository.save(entity);
            return new TeamOutputDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            teamRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(TeamInputDTO dto, Team entity) {
        Group group = groupRepository.findByName(dto.getGroup()).orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        entity.setName(dto.getName());
        entity.setAcronym(dto.getAcronym().toUpperCase());
        entity.setFlagUrl(dto.getFlagUrl());

        if (group.getTeams().size() < 4) {
            entity.setGroup(group);
        } else {
            throw new DatabaseException("Group already has 4 teams");
        }
    }
}
