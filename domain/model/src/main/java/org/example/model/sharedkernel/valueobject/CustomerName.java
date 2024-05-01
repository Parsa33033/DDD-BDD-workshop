package org.example.model.sharedkernel.valueobject;

import javax.validation.constraints.NotNull;
import org.example.framework.model.SimpleValueObject;

public class CustomerName implements SimpleValueObject<CustomerName, String> {

  @NotNull
  private String value;

  public CustomerName(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  public static CustomerName create(String value) {
    CustomerName customerName = new CustomerName(value);
    return customerName;
  }
}
