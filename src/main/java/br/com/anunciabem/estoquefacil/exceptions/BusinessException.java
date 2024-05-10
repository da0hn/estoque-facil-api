package br.com.anunciabem.estoquefacil.exceptions;

import java.io.Serial;

public abstract class BusinessException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 5549844035930448359L;

  protected BusinessException() {
  }

  protected BusinessException(final String message) {
    super(message);
  }

  protected BusinessException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
