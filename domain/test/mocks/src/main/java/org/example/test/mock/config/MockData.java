package org.example.test.mock.config;

import java.util.Map;
import org.example.test.mock.config.failure.TestFailure;
import org.immutables.value.Value.Immutable;

public interface MockData<D> {

  D data();

  Map<RepositoryOperation, TestFailure> failureMap();
}
