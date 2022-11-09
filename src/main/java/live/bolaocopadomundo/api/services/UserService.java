package live.bolaocopadomundo.api.services;

import live.bolaocopadomundo.api.dto.user.UserDTO;
import live.bolaocopadomundo.api.dto.user.UserInsertDTO;
import live.bolaocopadomundo.api.dto.user.UserPasswordDTO;
import live.bolaocopadomundo.api.entities.User;
import live.bolaocopadomundo.api.repositories.RoleRepository;
import live.bolaocopadomundo.api.repositories.UserRepository;
import live.bolaocopadomundo.api.services.email.EmailService;
import live.bolaocopadomundo.api.services.exceptions.DatabaseException;
import live.bolaocopadomundo.api.services.exceptions.ResourceNotFoundException;
import live.bolaocopadomundo.api.services.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        User entity = user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new UserDTO(entity);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional
    public String create(UserInsertDTO dto) {
        User entity = new User();
        entity.setNickname(dto.getNickname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.getRoles().add(roleRepository.getOne(1L));
        entity.setActivationCode(getRandomActivationCode());
        entity = userRepository.save(entity);

        emailService.sendConfirmationEmail(entity);
        return tokenService.generateEmailToken(entity);
    }

    @Transactional
    public UserDTO changePassword(UserPasswordDTO dto) {
        try {
            String userEmail = tokenService.getUserEmail(dto.getToken());
            User entity = userRepository.findByEmail(userEmail).get();
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User not found ");
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("Email not found");
    }

    public static String getRandomActivationCode() {
        Random randomizer = new Random();
        int number = randomizer.nextInt(999999);
        return String.format("%06d", number);
    }

    public Boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean passwordsMatch(String password, String userPassword) {
        return passwordEncoder.matches(password, userPassword);
    }
}
