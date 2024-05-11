package br.com.anunciabem.estoquefacil.services;

import br.com.anunciabem.estoquefacil.dto.UserSummaryResponse;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchUserById {

  @Transactional
  UserSummaryResponse execute(final Long id);

}
