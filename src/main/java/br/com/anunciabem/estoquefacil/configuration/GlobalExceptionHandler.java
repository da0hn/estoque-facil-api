package br.com.anunciabem.estoquefacil.configuration;

import br.com.anunciabem.estoquefacil.domain.exceptions.BusinessException;
import br.com.anunciabem.estoquefacil.domain.exceptions.ResourceNotFoundException;
import br.com.anunciabem.estoquefacil.dto.ApiFailureResponse;
import br.com.anunciabem.estoquefacil.dto.ApiValidationFailureResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
  public ResponseEntity<ApiFailureResponse<Void>> handleConflict(final RuntimeException exception) {
    exception.printStackTrace();
    return ResponseEntity.status(HttpStatus.CONFLICT)
      .body(ApiFailureResponse.of(exception.getMessage(), HttpStatus.CONFLICT));
  }

  @ExceptionHandler({ RuntimeException.class, Exception.class })
  public ResponseEntity<ApiFailureResponse<Void>> handleUnexpectedException(final RuntimeException exception) {
    exception.printStackTrace();
    return ResponseEntity.internalServerError()
      .body(ApiFailureResponse.of(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiFailureResponse<Void>> handleBusinessException(final BusinessException exception) {
    exception.printStackTrace();
    return ResponseEntity.badRequest().body(ApiFailureResponse.of(exception.getMessage(), HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiFailureResponse<Void>> handleResourceNotFoundException(final ResourceNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiFailureResponse.of(exception.getMessage(), HttpStatus.NOT_FOUND));
  }

  @ExceptionHandler({ SignatureException.class, ExpiredJwtException.class, MalformedJwtException.class })
  public ResponseEntity<ApiFailureResponse<Void>> handleJwtExceptions(final RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiFailureResponse.of(exception.getMessage(), HttpStatus.FORBIDDEN));
  }

  @ExceptionHandler({ BadCredentialsException.class })
  public ResponseEntity<ApiFailureResponse<Void>> handleBadCredentialsException(final BadCredentialsException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiFailureResponse.of(exception.getMessage(), HttpStatus.FORBIDDEN));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    final MethodArgumentNotValidException ex,
    final HttpHeaders headers,
    final HttpStatusCode status,
    final WebRequest request
  ) {
    final var fieldErrors = ex.getBindingResult().getFieldErrors();

    final var response = fieldErrors.stream()
      .map(error -> new ApiValidationFailureResponse(
        error.getField(),
        ObjectUtils.nullSafeToString(error.getRejectedValue()),
        error.getDefaultMessage()
      ))
      .collect(Collectors.toSet());

    return ResponseEntity.badRequest().body(ApiFailureResponse.of("Validation failed", HttpStatus.BAD_REQUEST, response));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
    final HttpMessageNotReadableException ex,
    final HttpHeaders headers,
    final HttpStatusCode status,
    final WebRequest request
  ) {
    return ResponseEntity.unprocessableEntity().body(ApiFailureResponse.of(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY));
  }

}
