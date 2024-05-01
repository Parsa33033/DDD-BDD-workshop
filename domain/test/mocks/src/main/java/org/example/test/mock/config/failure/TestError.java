package org.example.test.mock.config.failure;

import org.example.framework.error.Error;
import org.example.framework.error.OperationError;

public enum TestError implements TestFailure {
  ERROR(Error.of(OperationError.OTHER));

  private final Error error;

  TestError(Error error) {
    this.error = error;
  }

  public Error error() {
    return this.error;
  }
}
