package org.example.test.mock.config;

import org.example.test.data.config.TestData;
import org.example.test.mock.config.failure.TestFailure;

public interface MockSetup<TD extends TestData> {

  void setupMockData(MockData<TD> mockData);
  void mockSuccess(MockData<TD> mockData);
  void mockFailure(TestFailure failure);
}
