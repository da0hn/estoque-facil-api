package br.com.anunciabem.estoquefacil.configuration;

import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.ApiValidationFailureResponse;
import br.com.anunciabem.estoquefacil.exceptions.BusinessException;
import br.com.anunciabem.estoquefacil.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
  public ResponseEntity<ApiDataResponse<?>> handleConflict(final RuntimeException exception) {
    exception.printStackTrace();
    return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ApiDataResponse.failure(exception.getMessage()));
  }

  @ExceptionHandler({ RuntimeException.class, Exception.class })
  public ResponseEntity<ApiDataResponse<?>> handleUnexpectedException(final RuntimeException exception) {
    exception.printStackTrace();
    return ResponseEntity.internalServerError().body(ApiDataResponse.failure(exception.getMessage()));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiDataResponse<?>> handleBusinessException(final BusinessException exception) {
    exception.printStackTrace();
    return ResponseEntity.badRequest().body(ApiDataResponse.failure(exception.getMessage()));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiDataResponse<?>> handleResourceNotFoundException(final ResourceNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiDataResponse.failure(exception.getMessage()));
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
      .map(error -> new ApiValidationFailureResponse(error.getField(), error.getDefaultMessage(), error.getRejectedValue().toString()))
      .collect(Collectors.toSet());

    return ResponseEntity.badRequest().body(ApiDataResponse.of(response));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
    final HttpMessageNotReadableException ex,
    final HttpHeaders headers,
    final HttpStatusCode status,
    final WebRequest request
  ) {
    return ResponseEntity.badRequest().body(ApiDataResponse.failure(ex.getMessage()));
  }

}
