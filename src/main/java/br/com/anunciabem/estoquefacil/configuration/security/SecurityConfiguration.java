package br.com.anunciabem.estoquefacil.configuration.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(
  securedEnabled = true,
  jsr250Enabled = true
)
@AllArgsConstructor
public class SecurityConfiguration {

  private final UserDetailsService userDetailsService;

  private final UnauthorizedHandler unauthorizedHandler;

  private final JwtService jwtService;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    final var provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(this.userDetailsService);
    provider.setPasswordEncoder(this.passwordEncoder());
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public DefaultSecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf(AbstractHttpConfigurer::disable)
      .exceptionHandling(it -> it.authenticationEntryPoint(this.unauthorizedHandler))
      .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(it -> it.requestMatchers("/actuator/**").permitAll()
        .requestMatchers("/**/swagger-ui/**").permitAll()
        .requestMatchers("/**/swagger-resources/**").permitAll()
        .requestMatchers("/**/v2/api-docs").permitAll()
        .requestMatchers("/auth/**").permitAll()
        .anyRequest().authenticated()
      )
      .authenticationProvider(this.authenticationProvider())
      .addFilterBefore(this.authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  private AuthenticationTokenFilter authenticationTokenFilter() {
    return new AuthenticationTokenFilter(this.userDetailsService, this.jwtService);
  }

}
