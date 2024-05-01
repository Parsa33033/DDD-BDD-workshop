package org.example.framework.error;

public interface ErrorMessageInitializer extends ErrorMessage {

  void initialize();

  void addMessage(String code, String message);
}
