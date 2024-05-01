package org.example.framework.error;

import static org.example.framework.error.ImmutableError.tryGetMessageForCode;

import org.example.framework.DomainConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationError implements Error {

  Logger logger = LoggerFactory.getLogger(OperationError.class);

  public static String OTHER = ImmutableError.OTHER.code();

  public final Error error;
  public final String code;
  public String message;

  protected OperationError(String code) {
    this.code = code;
    this.error = Error.of(code);
    this.message = tryGetMessageForCode(code);
    logger.debug("error code: {}, error message: {}", code, message);
  }

  protected OperationError(String code, String message) {
    this(code);
    this.message = message;
  }

  @Override
  public String code() {
    return this.code;
  }

  @Override
  public String message() {
    return this.message;
  }

}
