package org.example.framework.error;


import org.example.framework.DomainConfig;

public abstract class ErrorCodeMessageInitializer implements ErrorMessageInitializer {

  @Override
  public void addMessage(final String code, final String message) {
    DomainConfig.instance().getLOV().put(code, message);
  }
}
