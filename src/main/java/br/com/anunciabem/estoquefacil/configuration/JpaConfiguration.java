package br.com.anunciabem.estoquefacil.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfiguration {

  @Bean
  public AuditorAware<String> auditorAware() {
    return () -> {
      final var principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return Optional.ofNullable(principal.getUsername());
    };
  }

}
