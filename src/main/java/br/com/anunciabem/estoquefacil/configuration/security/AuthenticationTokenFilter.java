package br.com.anunciabem.estoquefacil.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;

  private final JwtService jwtService;

  private String parseJwt(final HttpServletRequest request) {
    final var authenticationHeader = request.getHeader("Authorization");
    if (!StringUtils.hasText(authenticationHeader) || !authenticationHeader.startsWith("Bearer ")) {
      throw new IllegalArgumentException("Jwt token is missing or invalid");
    }
    return authenticationHeader.substring(7);
  }

  @Override
  protected void doFilterInternal(
    final HttpServletRequest request,
    final HttpServletResponse response,
    final FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      final var jwt = this.parseJwt(request);
      final var username = this.jwtService.extractUsername(jwt);
      final var userDetails = this.userDetailsService.loadUserByUsername(username);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        if (this.jwtService.validateToken(jwt, userDetails)) {
          final var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }
    catch (final Exception e) {
      this.logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

}
