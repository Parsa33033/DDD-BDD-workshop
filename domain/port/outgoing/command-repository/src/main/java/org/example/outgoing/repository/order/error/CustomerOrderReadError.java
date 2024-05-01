package org.example.outgoing.repository.order.error;

import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerOrderReadError extends OperationError {

  public static String INVALID_REQUEST = OperationErrorCode.INVALID_REQUEST;
  public static String ORDER_NOT_FOUND = OperationErrorCode.ORDER_NOT_FOUND;
  public static String CUSTOMER_NOT_FOUND = OperationErrorCode.CUSTOMER_NOT_FOUND;

  protected CustomerOrderReadError(final String code) {
    super(code);
  }

  public static CustomerOrderReadError of(String code) {
    return new CustomerOrderReadError(code);
  }
}
