package br.com.anunciabem.estoquefacil.domain.constraints;

import br.com.anunciabem.estoquefacil.domain.exceptions.InvalidPreConditionException;

import java.util.Collection;
import java.util.function.Predicate;

public final class ValidationUtils {

  private ValidationUtils() { }

  public static void requireNonNull(final Object object, final String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void requireNonEmpty(final String string, final String message) {
    if (string == null || string.isBlank()) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void requireNonEmpty(final Collection<?> collection, final String message) {
    if (collection == null || collection.isEmpty()) {
      throw new IllegalArgumentException(message);
    }
  }

  public static <T> void preCondition(final T object, final Predicate<T> predicate, final String message) {
    if (predicate.test(object)) {
      throw new InvalidPreConditionException(message);
    }
  }

}
