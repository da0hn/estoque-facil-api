package br.com.anunciabem.estoquefacil.services;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.configuration.security.JwtService;
import br.com.anunciabem.estoquefacil.dto.LoginParameter;
import br.com.anunciabem.estoquefacil.dto.LoginResponse;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@AllArgsConstructor
public class AuthenticateUseCaseImpl implements AuthenticateUseCase {

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  private final JwtService jwtService;

  @Override
  @Transactional
  public LoginResponse execute(@Valid final LoginParameter parameter) {
    log.debug("m=authenticate(parameter={})", parameter);

    final var user = this.userRepository.findByUsernameOrElseThrow(parameter.username());

    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(parameter.username(), parameter.password()));

    return LoginResponse.builder()
      .token(this.jwtService.generateToken(user, JwtService.TokenType.ACCESS))
      .refreshToken(this.jwtService.generateToken(user, JwtService.TokenType.REFRESH))
      .email(user.getEmail())
      .username(user.getUsername())
      .role(user.getRole())
      .name(user.getName())
      .userId(user.getId())
      .build();
  }

}
