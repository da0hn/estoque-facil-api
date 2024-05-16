package br.com.anunciabem.estoquefacil.services.users;

import br.com.anunciabem.estoquefacil.dto.SearchUserCriteria;
import br.com.anunciabem.estoquefacil.dto.UserSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchUsersUseCase {

  @Transactional
  Page<UserSummaryResponse> execute(
    SearchUserCriteria criteria,
    Pageable pageable
  );

}
