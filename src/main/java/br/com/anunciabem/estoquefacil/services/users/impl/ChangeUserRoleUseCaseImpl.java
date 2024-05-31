package br.com.anunciabem.estoquefacil.services.users.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.user.ChangeUserRoleParameter;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import br.com.anunciabem.estoquefacil.services.users.ChangeUserRoleUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class ChangeUserRoleUseCaseImpl implements ChangeUserRoleUseCase {

  private final UserRepository userRepository;

  @Override
  public void execute(final ChangeUserRoleParameter parameter) {
    log.debug("m=execute(userId={}, role={})", parameter.userId(), parameter.role());

    final var user = this.userRepository.findByIdOrElseThrow(parameter.userId());

    user.changeRole(parameter.role());

    this.userRepository.save(user);
  }

}
