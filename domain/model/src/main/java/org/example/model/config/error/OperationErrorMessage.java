package org.example.model.config.error;

import org.example.framework.error.ErrorMessage;

public interface OperationErrorMessage extends ErrorMessage {
  public static String MSG_OTHER = "error is other";
  public static String MSG_INVALID_REQUEST = "request is invalid";
  public static String MSG_STORAGE_NOT_FOUND = "storage not found";

  public static String MSG_CUSTOMER_NOT_FOUND = "customer is not found";
  public static String MSG_CUSTOMER_EXISTS = "customer already exists";
}
