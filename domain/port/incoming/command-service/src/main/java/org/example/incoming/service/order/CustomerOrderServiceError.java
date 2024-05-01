package org.example.incoming.service.order;

import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerOrderServiceError extends OperationError {

  public static String CUSTOMER_EXISTS = OperationErrorCode.CUSTOMER_EXISTS;

  protected CustomerOrderServiceError(final String code) {
    super(code);
  }

  public static CustomerOrderServiceError of(String code) {
    return new CustomerOrderServiceError(code);
  }
}
