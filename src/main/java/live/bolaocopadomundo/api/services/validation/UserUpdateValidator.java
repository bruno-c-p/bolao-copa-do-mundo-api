package live.bolaocopadomundo.api.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import live.bolaocopadomundo.api.dto.UserUpdateDTO;
import live.bolaocopadomundo.api.entities.User;
import live.bolaocopadomundo.api.repositories.UserRepository;
import live.bolaocopadomundo.api.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void initialize(UserUpdateValid ann) {
  }

  @Override
  public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
    @SuppressWarnings("unchecked")
    var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    long userId = Long.parseLong(uriVars.get("id"));

    List<FieldMessage> list = new ArrayList<>();
    Optional<User> emailUser = userRepository.findByEmail(dto.getEmail());
    Optional<User> nicknameUser = userRepository.findByNickname(dto.getEmail());

    if (emailUser.isPresent() && emailUser.get().getId() != userId) {
      list.add(new FieldMessage("email", "Email already exists!"));
    }

    if (nicknameUser.isPresent() && nicknameUser.get().getId() != userId) {
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
