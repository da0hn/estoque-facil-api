package br.com.anunciabem.estoquefacil.configuration;

import br.com.anunciabem.estoquefacil.configuration.resolvers.CurrentUserArgumentResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

  private final CurrentUserArgumentResolver currentUserArgumentResolver;

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    // https://reflectoring.io/spring-cors/
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods(
        "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD", "TRACE", "CONNECT"
      );
  }

  @Override
  public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(this.currentUserArgumentResolver);
  }

}
