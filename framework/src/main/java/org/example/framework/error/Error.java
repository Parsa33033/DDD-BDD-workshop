package org.example.framework.error;

import java.util.Objects;

public interface Error {

  static Error of(String code) {
    Objects.requireNonNull(code, "code is required");
    return ImmutableError.of(code);
  }

  String code();

  String message();
}
