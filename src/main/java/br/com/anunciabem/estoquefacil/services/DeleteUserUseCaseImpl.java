package br.com.anunciabem.estoquefacil.services;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

  private final UserRepository userRepository;

  @Override
  public void execute(final Long userId) {
    log.debug("m=execute(userId={})", userId);
    final var user = this.userRepository.findByIdOrElseThrow(userId);
    this.userRepository.delete(user);
  }

}
