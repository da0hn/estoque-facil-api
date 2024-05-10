package br.com.anunciabem.estoquefacil.configuration.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String extractUsername(String token);

  boolean validateToken(String token, UserDetails userDetails);

  String generateToken(UserDetails username, TokenType type);

  enum TokenType {
    ACCESS,
    REFRESH
  }

}
