package br.com.anunciabem.estoquefacil.configuration.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

  private final UserDetailsService userDetailsService;

  private final AuthenticationTokenFilter authenticationTokenFilter;

  private final UnauthorizedHandler unauthorizedHandler;

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
  public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(it -> it.requestMatchers("/actuator/**").permitAll()
        .requestMatchers("/**/swagger-ui/**").permitAll()
        .requestMatchers("/**/swagger-resources/**").permitAll()
        .requestMatchers("/**/v2/api-docs").permitAll()
        .requestMatchers("/auth/register").permitAll()
        .requestMatchers("/auth/token").permitAll()
        .anyRequest().authenticated()
      );
    httpSecurity.addFilterBefore(this.authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    httpSecurity.authenticationProvider(this.authenticationProvider());
    return httpSecurity.build();
  }

}
