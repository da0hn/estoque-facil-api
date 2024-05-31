package br.com.anunciabem.estoquefacil.services.users.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.entities.User;
import br.com.anunciabem.estoquefacil.dto.user.RegisterUserParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import br.com.anunciabem.estoquefacil.services.users.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Slf4j
@UseCase
@Validated
@AllArgsConstructor
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  @Override
  @Transactional
  public ResourceCreated execute(@Valid final RegisterUserParameter parameter) {
    log.debug("m=execute(parameter={})", parameter);

    if (!parameter.password().equals(parameter.confirmationPassword())) {
      throw new RuntimeException("Password and confirmation password do not match.");
    }

    final var newUser = User.builder()
      .id(null)
      .username(parameter.username())
      .name(parameter.name())
      .email(parameter.email())
      .password(this.passwordEncoder.encode(parameter.password()))
      .role(parameter.role())
      .createdAt(Instant.now())
      .build();

    this.userRepository.save(newUser);

    return new ResourceCreated(newUser.getId());
  }

}
