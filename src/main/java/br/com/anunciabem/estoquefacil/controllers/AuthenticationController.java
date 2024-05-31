package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.user.LoginParameter;
import br.com.anunciabem.estoquefacil.dto.user.LoginResponse;
import br.com.anunciabem.estoquefacil.services.users.AuthenticateUseCase;
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

  @PostMapping("/token")
  public ResponseEntity<ApiDataResponse<LoginResponse>> authenticate(@Valid @RequestBody final LoginParameter parameter) {
    final var response = this.authenticateUseCase.execute(parameter);

    return ResponseEntity.ok(ApiDataResponse.of(response));
  }

}
