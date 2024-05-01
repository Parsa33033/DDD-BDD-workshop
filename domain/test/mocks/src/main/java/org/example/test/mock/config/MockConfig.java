package org.example.test.mock.config;

import java.util.List;
import org.example.framework.repository.Repository;
import org.example.test.data.config.TestData;

public interface MockConfig<TD extends TestData, R extends Repository> {


  R repository();
  void setupMockData(MockData<TD> mockData);
}
