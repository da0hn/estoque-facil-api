package br.com.anunciabem.estoquefacil.exceptions;

import java.io.Serial;

public class BusinessValidationException extends BusinessException {

  @Serial
  private static final long serialVersionUID = -3316648526222903512L;

  public BusinessValidationException() {
  }

  public BusinessValidationException(final String message) {
    super(message);
  }

  public BusinessValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
