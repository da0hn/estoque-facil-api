package br.com.anunciabem.estoquefacil.services.users.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.constraints.ValidationUtils;
import br.com.anunciabem.estoquefacil.dto.UserSummaryResponse;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import br.com.anunciabem.estoquefacil.services.users.SearchUserById;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchUserByIdImpl implements SearchUserById {

  private final UserRepository userRepository;

  @Override
  public UserSummaryResponse execute(final Long userId) {
    ValidationUtils.requireNonNull(userId, "user id is required");
    log.debug("m=execute(userId={})", userId);

    final var user = this.userRepository.findByIdOrElseThrow(userId);

    return UserSummaryResponse.builder()
      .id(user.getId())
      .name(user.getName())
      .username(user.getUsername())
      .email(user.getEmail())
      .role(user.getRole())
      .createdAt(user.getCreatedAt())
      .updatedAt(user.getUpdatedAt())
      .build();
  }

}
