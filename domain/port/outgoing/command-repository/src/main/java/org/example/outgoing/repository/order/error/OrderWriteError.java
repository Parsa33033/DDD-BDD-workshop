package org.example.outgoing.repository.order.error;

import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class OrderWriteError extends OperationError {

  public static String INVALID_REQUEST = OperationErrorCode.INVALID_REQUEST;
  public static String ORDER_EXISTS = OperationErrorCode.ORDER_EXISTS;

  protected OrderWriteError(final String code) {
    super(code);
  }

  public static OrderWriteError of(String code) {
    return new OrderWriteError(code);
  }
}
