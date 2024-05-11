package br.com.anunciabem.estoquefacil.services;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.ChangeUserRoleParameter;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
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
