package br.com.anunciabem.estoquefacil.services.users.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.user.ChangeUserPasswordParameter;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import br.com.anunciabem.estoquefacil.services.users.ChangeUserPasswordUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@UseCase
@AllArgsConstructor
public class ChangeUserPasswordUseCaseImpl implements ChangeUserPasswordUseCase {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public void execute(@Valid @NotNull final ChangeUserPasswordParameter parameter) {
    log.debug("m=execute(userId={})", parameter.userId());

    final var user = this.userRepository.findByIdOrElseThrow(parameter.userId());

    user.changePassword(parameter.password(), this.passwordEncoder);

    this.userRepository.save(user);
  }

}
