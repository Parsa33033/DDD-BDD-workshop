package org.example.test.data.config;

import org.example.framework.dto.DataTransferObject;
import org.example.framework.error.Error;
import org.example.framework.result.Result;

public class TestContext {
  private TestData testData;
  private Result<?, Error> result;

  public TestContext() {
    testData = new TestDataInMemory();
  }

  public TestData testData() {
    return this.testData;
  }

  public Result<?, Error> result() {
    return this.result;
  }

  public void result(Result<?, Error> result) {
    this.result = result;
  }
}
