package live.bolaocopadomundo.api.services;

import live.bolaocopadomundo.api.dto.group.GroupDTO;
import live.bolaocopadomundo.api.dto.group.GroupInputDTO;
import live.bolaocopadomundo.api.dto.group.GroupOutputDTO;
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
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public Page<GroupOutputDTO> findAllPaged(Pageable pageable) {
        Page<Group> list = groupRepository.findAll(pageable);
        return list.map(GroupOutputDTO::new);
    }

    @Transactional(readOnly = true)
    public GroupOutputDTO findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        Group entity = group.orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        return new GroupOutputDTO(entity);
    }

    @Transactional
    public GroupOutputDTO insert(GroupInputDTO dto) {
        Group entity = new Group();
        entity.setName(dto.getName());
        entity = groupRepository.save(entity);
        return new GroupOutputDTO(entity);
    }

    @Transactional
    public GroupOutputDTO update(Long id, GroupInputDTO dto) {
        try {
            Group entity = groupRepository.getOne(id);
            entity.setName(dto.getName());
            entity = groupRepository.save(entity);
            return new GroupOutputDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            groupRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
