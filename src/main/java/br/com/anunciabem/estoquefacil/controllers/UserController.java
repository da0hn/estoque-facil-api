package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.ApiPageResponse;
import br.com.anunciabem.estoquefacil.dto.RegisterUserParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.dto.SearchUserCriteria;
import br.com.anunciabem.estoquefacil.dto.UserSummaryResponse;
import br.com.anunciabem.estoquefacil.services.RegisterUserUseCase;
import br.com.anunciabem.estoquefacil.services.SearchUsersUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final SearchUsersUseCase searchUsersUseCase;

  private final RegisterUserUseCase registerUserUseCase;

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

  @PostMapping
  public ResponseEntity<ApiDataResponse<ResourceCreated>> register(@Valid @RequestBody final RegisterUserParameter parameter) {
    final var response = this.registerUserUseCase.execute(parameter);

    return ResponseEntity.ok(
      ApiDataResponse.<ResourceCreated>builder()
        .message("User created successfully.")
        .data(response)
        .build()
    );
  }

}
