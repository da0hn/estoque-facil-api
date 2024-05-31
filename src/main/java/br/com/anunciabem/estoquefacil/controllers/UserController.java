package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.ApiPageResponse;
import br.com.anunciabem.estoquefacil.dto.user.ChangeUserPasswordParameter;
import br.com.anunciabem.estoquefacil.dto.user.ChangeUserPasswordRequest;
import br.com.anunciabem.estoquefacil.dto.user.ChangeUserRoleParameter;
import br.com.anunciabem.estoquefacil.dto.user.ChangeUserRoleRequest;
import br.com.anunciabem.estoquefacil.dto.user.RegisterUserParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.dto.user.SearchUserCriteria;
import br.com.anunciabem.estoquefacil.dto.user.UserSummaryResponse;
import br.com.anunciabem.estoquefacil.services.users.ChangeUserPasswordUseCase;
import br.com.anunciabem.estoquefacil.services.users.ChangeUserRoleUseCase;
import br.com.anunciabem.estoquefacil.services.users.DeleteUserUseCase;
import br.com.anunciabem.estoquefacil.services.users.RegisterUserUseCase;
import br.com.anunciabem.estoquefacil.services.users.SearchUserById;
import br.com.anunciabem.estoquefacil.services.users.SearchUsersUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  private final DeleteUserUseCase deleteUserUseCase;

  private final SearchUserById searchUserById;

  private final ChangeUserPasswordUseCase changeUserPasswordUseCase;

  private final ChangeUserRoleUseCase changeUserRoleUseCase;

  @GetMapping
  public ResponseEntity<ApiPageResponse<UserSummaryResponse>> searchUsers(
    @RequestParam(name = "name", required = false, defaultValue = "") final String name,
    @RequestParam(name = "username", required = false, defaultValue = "") final String username,
    @RequestParam(name = "email", required = false, defaultValue = "") final String email,
    @RequestParam(name = "search-text", required = false, defaultValue = "") final String searchText,
    @ParameterObject final Pageable pageable
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

    if (!output.hasContent()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(ApiPageResponse.of(output));
  }

  @PostMapping
  public ResponseEntity<ApiDataResponse<ResourceCreated>> register(@Valid @RequestBody final RegisterUserParameter parameter) {
    final var output = this.registerUserUseCase.execute(parameter);

    return ResponseEntity.ok(
      ApiDataResponse.<ResourceCreated>builder()
        .message("User created successfully.")
        .data(output)
        .build()
    );
  }

  @DeleteMapping("/{user-id}")
  public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable("user-id") final Long id) {
    this.deleteUserUseCase.execute(id);
    return ResponseEntity.ok(
      ApiDataResponse.onlyMessage("User %d successfully removed.")
    );
  }

  @GetMapping("/{user-id}")
  public ResponseEntity<ApiDataResponse<UserSummaryResponse>> searchUserById(@PathVariable("user-id") final Long userId) {
    final var output = this.searchUserById.execute(userId);
    return ResponseEntity.ok(ApiDataResponse.of(output));
  }

  @PatchMapping("/{user-id}/change-password")
  public ResponseEntity<ApiDataResponse<Void>> changePassword(
    @PathVariable("user-id") final Long userId,
    @Valid @RequestBody final ChangeUserPasswordRequest request
  ) {
    this.changeUserPasswordUseCase.execute(
      ChangeUserPasswordParameter.builder()
        .userId(userId)
        .password(request.password())
        .build()
    );
    return ResponseEntity.accepted().body(ApiDataResponse.onlyMessage("Password successfully updated."));
  }

  @PatchMapping("/{user-id}/role")
  public ResponseEntity<ApiDataResponse<Void>> changeRole(
    @PathVariable("user-id") final Long userId,
    @Valid @RequestBody final ChangeUserRoleRequest request
  ) {
    this.changeUserRoleUseCase.execute(
      ChangeUserRoleParameter.builder()
        .userId(userId)
        .role(request.role())
        .build()
    );
    return ResponseEntity.accepted().body(ApiDataResponse.onlyMessage("Role successfully updated."));
  }

}
