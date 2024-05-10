package br.com.anunciabem.estoquefacil.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends BusinessException {

  @Serial
  private static final long serialVersionUID = -8225285479208815644L;

  public ResourceNotFoundException() {
  }

  public ResourceNotFoundException(final String message) {
    super(message);
  }

  public ResourceNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
