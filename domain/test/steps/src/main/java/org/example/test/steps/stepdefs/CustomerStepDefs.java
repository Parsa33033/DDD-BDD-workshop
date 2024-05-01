package org.example.test.steps.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.example.dto.CustomerData;
import org.example.dto.ImmutableCustomerData;
import org.example.test.data.config.TestContext;
import org.example.test.data.model.TestCustomerData;
import org.example.test.persona.testdata.TestCustomer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

public class CustomerStepDefs {

  TestContext testContext;

  @Autowired
  public CustomerStepDefs(TestContext testContext) {
    this.testContext = testContext;
  }

  @Given("Customer {testCustomer}")
  public void givenCustomer(TestCustomer testCustomer) {
    testContext.testData().addCustomer(testCustomer.customerData());
  }

  public static CustomerData createCustomerData(DataTable dataTable) {
    Map<String, String> dataMap = dataTable.asMaps().get(0);
    UUID customerIdentifier = UUID.fromString(dataMap.get("customer_identifier"));
    String customerName = dataMap.get("customer_name");
    CustomerData customer = new TestCustomerBuilder(
        customerIdentifier,
        customerName).customerData();
    return customer;
  }

  private static class TestCustomerBuilder implements TestCustomerData {

    UUID identifier;
    String name;

    public TestCustomerBuilder(UUID identifier, String name) {
      this.identifier = identifier;
      this.name = name;
    }

    @Override
    public CustomerData customerData() {
      return ImmutableCustomerData.builder().identifier(identifier).name(name).build();
    }
  }
}
