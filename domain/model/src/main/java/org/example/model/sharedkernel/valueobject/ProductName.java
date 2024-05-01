package org.example.model.sharedkernel.valueobject;

import javax.validation.constraints.NotNull;
import org.example.framework.model.SimpleValueObject;

public class ProductName implements SimpleValueObject<ProductName, String> {

  @NotNull
  private String value;

  public ProductName(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  public static ProductName create(String value) {
    ProductName productName = new ProductName(value);
    return productName;
  }
}
