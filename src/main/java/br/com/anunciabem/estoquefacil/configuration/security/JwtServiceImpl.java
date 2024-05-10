package br.com.anunciabem.estoquefacil.configuration.security;

import br.com.anunciabem.estoquefacil.configuration.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final JwtProperties jwtProperties;

  @Override
  public String extractUsername(final String token) {
    return this.extractClaim(token, Claims::getSubject);
  }

  @Override
  public boolean validateToken(final String token, final UserDetails userDetails) {
    final String username = this.extractUsername(token);
    try {
      Jwts.parserBuilder()
        .setSigningKey(this.getSignKey())
        .build()
        .parse(token);
      return (username.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
    }
    catch (final MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    }
    catch (final ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    }
    catch (final UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    }
    catch (final IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  @Override
  public String generateToken(final UserDetails userDetails, final TokenType type) {
    return this.createToken(userDetails, type);
  }

  private <T> T extractClaim(final String token, final Function<? super Claims, T> claimsResolver) {
    final var claims = this.extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private String createToken(final UserDetails userDetails, final TokenType type) {
    final var authorities = userDetails.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .toList();
    return Jwts.builder()
      .claim("authorities", authorities)
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setIssuer("estoque-facil")
      .setExpiration(this.getExpiration(type))
      .signWith(this.getSignKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  private Boolean isTokenExpired(final String token) {
    return this.extractExpiration(token).before(new Date());
  }

  private Date getExpiration(final TokenType type) {
    return switch (type) {
      case ACCESS -> new Date(System.currentTimeMillis() + this.jwtProperties.expiration().toMillis());
      case REFRESH -> new Date(System.currentTimeMillis() + this.jwtProperties.refreshExpiration().toMillis());
    };
  }

  private Date extractExpiration(final String token) {
    return this.extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(final String token) {
    return Jwts.parserBuilder()
      .setSigningKey(this.getSignKey())
      .build()
      .parseClaimsJws(token)
      .getBody();

  }

  private SecretKey getSignKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtProperties.secret()));
  }

}
