package live.bolaocopadomundo.api.services;

import live.bolaocopadomundo.api.dto.RoleDTO;
import live.bolaocopadomundo.api.entities.Role;
import live.bolaocopadomundo.api.repositories.RoleRepository;
import live.bolaocopadomundo.api.services.exceptions.DatabaseException;
import live.bolaocopadomundo.api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<RoleDTO> findAll() {
        List<Role> list = roleRepository.findAll();
        return list.stream().map(RoleDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public RoleDTO findById(Long id) {
        Optional<Role> object = roleRepository.findById(id);
        Role entity = object.orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return new RoleDTO(entity);
    }

    @Transactional
    public RoleDTO insert(RoleDTO dto) {
        Role entity = new Role();
        entity.setAuthority(dto.getAuthority());
        entity = roleRepository.save(entity);
        return new RoleDTO(entity);
    }

    @Transactional
    public RoleDTO update(Long id, RoleDTO dto) {
        try {
            Role entity = roleRepository.getOne(id);
            entity.setAuthority(dto.getAuthority());
            entity = roleRepository.save(entity);
            return new RoleDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
