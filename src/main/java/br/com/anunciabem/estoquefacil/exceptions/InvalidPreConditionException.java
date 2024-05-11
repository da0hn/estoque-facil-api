package br.com.anunciabem.estoquefacil.exceptions;

import java.io.Serial;

public class InvalidPreConditionException extends BusinessException {

  @Serial
  private static final long serialVersionUID = -8225285479208815644L;

  public InvalidPreConditionException() {
  }

  public InvalidPreConditionException(final String message) {
    super(message);
  }

  public InvalidPreConditionException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
