package live.bolaocopadomundo.api.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import live.bolaocopadomundo.api.dto.UserInsertDTO;
import live.bolaocopadomundo.api.entities.User;
import live.bolaocopadomundo.api.repositories.UserRepository;
import live.bolaocopadomundo.api.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

  @Autowired
  private UserRepository userRepository;

  @Override
  public void initialize(UserInsertValid ann) {
  }

  @Override
  public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

    List<FieldMessage> list = new ArrayList<>();
    Optional<User> emailUser = userRepository.findByEmail(dto.getEmail());
    Optional<User> nicknameUser = userRepository.findByNickname(dto.getEmail());

    if (emailUser.isPresent()) {
      list.add(new FieldMessage("email", "Email already exists!"));
    }

    if (nicknameUser.isPresent()) {
      list.add(new FieldMessage("nickname", "Nickname already exists!"));
    }

    for (FieldMessage e : list) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
          .addConstraintViolation();
    }
    return list.isEmpty();
  }
}
