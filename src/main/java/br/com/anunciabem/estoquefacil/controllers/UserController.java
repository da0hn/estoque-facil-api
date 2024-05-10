package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.dto.ApiPageResponse;
import br.com.anunciabem.estoquefacil.dto.SearchUserCriteria;
import br.com.anunciabem.estoquefacil.dto.UserSummaryResponse;
import br.com.anunciabem.estoquefacil.services.SearchUsersUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final SearchUsersUseCase searchUsersUseCase;

  @GetMapping
  public ResponseEntity<ApiPageResponse<UserSummaryResponse>> searchUsers(
    @RequestParam(required = false, defaultValue = "") final String name,
    @RequestParam(required = false, defaultValue = "") final String username,
    @RequestParam(required = false, defaultValue = "") final String email,
    @RequestParam(required = false, defaultValue = "") final String searchText,
    final Pageable pageable
  ) {
    final var output = this.searchUsersUseCase.execute(
      SearchUserCriteria.builder()
        .name(name)
        .username(username)
        .email(email)
        .searchText(searchText)
        .build(),
      pageable
    );
    return ResponseEntity.ok(ApiPageResponse.of(output));
  }

}
