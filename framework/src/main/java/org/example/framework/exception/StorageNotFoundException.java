package org.example.framework.exception;

public class StorageNotFoundException extends RuntimeException {

  public StorageNotFoundException(String message) {
    super(message);
  }
}
