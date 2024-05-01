package org.example.service.command.customerorder.domain.error;

import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerOrderError extends OperationError {

  public static String ORDER_ALREADY_EXISTS = OperationErrorCode.ORDER_EXISTS;
  public static String PRODUCT_OUT_OF_STOCK = OperationErrorCode.PRODUCT_OUT_OF_STOCK;

  protected CustomerOrderError(final String code) {
    super(code);
  }

  public static CustomerOrderError of(String code) {
    return new CustomerOrderError(code);
  }
}
