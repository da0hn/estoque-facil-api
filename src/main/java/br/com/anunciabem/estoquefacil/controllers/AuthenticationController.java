package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.LoginParameter;
import br.com.anunciabem.estoquefacil.dto.LoginResponse;
import br.com.anunciabem.estoquefacil.dto.RegisterUserParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.services.AuthenticateUseCase;
import br.com.anunciabem.estoquefacil.services.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticateUseCase authenticateUseCase;

  private final RegisterUserUseCase registerUserUseCase;

  @PostMapping("/token")
  public ResponseEntity<ApiDataResponse<LoginResponse>> authenticate(@Valid @RequestBody final LoginParameter parameter) {
    final var response = this.authenticateUseCase.execute(parameter);

    return ResponseEntity.ok(ApiDataResponse.of(response));
  }

  @PostMapping("/register")
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
