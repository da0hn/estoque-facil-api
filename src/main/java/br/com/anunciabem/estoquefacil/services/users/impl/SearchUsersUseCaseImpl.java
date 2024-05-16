package br.com.anunciabem.estoquefacil.services.users.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.SearchUserCriteria;
import br.com.anunciabem.estoquefacil.dto.UserSummaryResponse;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import br.com.anunciabem.estoquefacil.services.users.SearchUsersUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchUsersUseCaseImpl implements SearchUsersUseCase {

  private final UserRepository userRepository;

  @Override
  public Page<UserSummaryResponse> execute(
    final SearchUserCriteria criteria,
    final Pageable pageable
  ) {
    log.debug("m=execute(criteria={}, pageable={})", criteria, pageable);
    return this.userRepository.findAll(criteria.name(), criteria.username(), criteria.email(), criteria.searchText(), pageable)
      .map(user -> new UserSummaryResponse(
             user.getId(),
             user.getName(),
             user.getUsername(),
             user.getEmail(),
             user.getRole(),
             user.getCreatedAt(),
             user.getUpdatedAt()
           )
      );
  }

}
