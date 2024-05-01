package org.example.test.mock.config.failure;

import org.example.framework.exception.StorageNotFoundException;

public enum TestException implements TestFailure {
    STORAGE_NOT_FOUND(new StorageNotFoundException("not found"));

    private final Exception exception;

    TestException(Exception exception) {
        this.exception = exception;
    }

    public Exception exception() {
        return this.exception;
    }
}
