package br.com.anunciabem.estoquefacil.configuration.security;

import br.com.anunciabem.estoquefacil.exceptions.ResourceNotFoundException;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserDetailsServiceAdapter implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    log.debug("m=loadUserByUsername(username={})", username);
    return this.userRepository.findByUsername(username)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

}
