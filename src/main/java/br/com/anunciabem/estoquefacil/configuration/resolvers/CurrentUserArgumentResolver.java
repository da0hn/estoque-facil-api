package br.com.anunciabem.estoquefacil.configuration.resolvers;

import br.com.anunciabem.estoquefacil.configuration.resolvers.annotations.CurrentUser;
import br.com.anunciabem.estoquefacil.configuration.security.JwtConstants;
import br.com.anunciabem.estoquefacil.configuration.security.JwtService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final JwtService jwtService;

  public CurrentUserArgumentResolver(final JwtService jwtService) { this.jwtService = jwtService; }

  @Override
  public boolean supportsParameter(final MethodParameter parameter) {
    return parameter.hasMethodAnnotation(CurrentUser.class);
  }

  @Override
  public Object resolveArgument(
    final MethodParameter parameter,
    final ModelAndViewContainer mavContainer,
    final NativeWebRequest webRequest,
    final WebDataBinderFactory binderFactory
  ) throws Exception {
    final var authorizationHeaderContent = webRequest.getHeader("Authorization");
    if (!StringUtils.hasText(authorizationHeaderContent) || !authorizationHeaderContent.startsWith(JwtConstants.BEARER)) return null;
    final var token = authorizationHeaderContent.substring(JwtConstants.BEARER.length());
    return this.jwtService.extractUsername(token);
  }

}
