package org.example.framework.error;

import org.example.framework.DomainConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ImmutableError implements Error {
  Logger logger = LoggerFactory.getLogger(OperationError.class);

  public static final Error OTHER = ImmutableError.of("OTHER", "internal error");
  private final String code;
  private final String message;
  private final String hint;

  private ImmutableError(String code, String message, String hint) {
    this.code = code;
    this.message = message;
    this.hint = hint;
    logger.debug("error code: {}, error message: {}", code, message);
  }

  public static ImmutableError of(String code) {
    return new ImmutableError(code, tryGetMessageForCode(code), "");
  }

  @Override
  public String code() {
    return code;
  }

  @Override
  public String message() {
    return this.message;
  }

  public static ImmutableError of(String code, String hint) {
    return new ImmutableError(code, tryGetMessageForCode(code), hint);
  }

  public static String tryGetMessageForCode(String code) {
    return DomainConfig.instance().getLOV().get(code);
  }
}
