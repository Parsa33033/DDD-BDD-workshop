package org.example.test.persona.testdata;

import org.example.dto.CustomerData;
import org.example.test.data.model.TestCustomerData;
import org.example.test.persona.customer.JP;
import org.example.test.persona.customer.Parsa;

public enum TestCustomer implements TestCustomerData {
  JP(new JP()),
  Parsa(new Parsa());
  TestCustomerData testCustomerData;

  TestCustomer(TestCustomerData testCustomerData) {
    this.testCustomerData = testCustomerData;
  }

  @Override
  public CustomerData customerData() {
    return this.testCustomerData.customerData();
  }
}
