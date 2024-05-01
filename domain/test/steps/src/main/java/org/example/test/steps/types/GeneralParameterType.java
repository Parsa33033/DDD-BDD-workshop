package org.example.test.steps.types;

import io.cucumber.java.ParameterType;
import org.example.test.mock.config.failure.TestException;
import org.example.test.persona.testdata.TestCustomer;
import org.example.test.persona.testdata.TestProduct;

public class GeneralParameterType {

  @ParameterType("\\w*")
  public TestCustomer testCustomer(String name) {
    return TestCustomer.valueOf(name);
  }

  @ParameterType("\\w*")
  public TestProduct testProduct(String name) {
    return TestProduct.valueOf(name);
  }

  @ParameterType("\\w*")
  public TestException testException(String name) {
    return TestException.valueOf(name);
  }
}
