package org.example.model.config.error;

import static org.example.model.config.error.OperationErrorCode.*;
import static org.example.model.config.error.OperationErrorMessage.*;

import org.example.framework.error.ErrorCodeMessageInitializer;

public class DomainErrorCodeMessageInitializer extends ErrorCodeMessageInitializer {

  private static final DomainErrorCodeMessageInitializer instance =
      new DomainErrorCodeMessageInitializer();

  public static void init() {
    instance.initialize();
  }

  @Override
  public void initialize() {
    addFieldErrors();
  }

  private void addFieldErrors() {
    addMessage(OTHER, MSG_OTHER);
    addMessage(CUSTOMER_NOT_FOUND, MSG_CUSTOMER_NOT_FOUND);
    addMessage(CUSTOMER_EXISTS, MSG_CUSTOMER_EXISTS);
    addMessage(INVALID_REQUEST, MSG_INVALID_REQUEST);
  }
}
