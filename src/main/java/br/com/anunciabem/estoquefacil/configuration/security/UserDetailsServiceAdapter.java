package br.com.anunciabem.estoquefacil.configuration.security;

import br.com.anunciabem.estoquefacil.entities.User;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@AllArgsConstructor
public class UserDetailsServiceAdapter implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    log.debug("m=loadUserByUsername(username={})", username);
    return this.userRepository.findByUsername(username)
      .map(UserDetailsImpl::new)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  public record UserDetailsImpl(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return Stream.of(this.user.getRole())
        .map(it -> (GrantedAuthority) () -> "ROLE_" + it.name())
        .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
      return this.user.getPassword();
    }

    @Override
    public String getUsername() {
      return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }

  }

}
