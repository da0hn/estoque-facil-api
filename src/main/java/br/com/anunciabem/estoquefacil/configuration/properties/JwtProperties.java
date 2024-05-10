package br.com.anunciabem.estoquefacil.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(
  String secret,
  Duration expiration,
  Duration refreshExpiration
) {
}
