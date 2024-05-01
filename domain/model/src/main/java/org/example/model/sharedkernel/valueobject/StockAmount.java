package org.example.model.sharedkernel.valueobject;

import org.example.framework.model.SimpleValueObject;

import javax.validation.constraints.NotNull;

public class StockAmount implements SimpleValueObject<StockAmount, Integer> {

  @NotNull
  private Integer value;

  public StockAmount(Integer value) {
    this.value = value;
  }

  @Override
  public Integer value() {
    return value;
  }

  public static StockAmount create(Integer value) {
    StockAmount stockAmount = new StockAmount(value);
    return stockAmount;
  }
}
