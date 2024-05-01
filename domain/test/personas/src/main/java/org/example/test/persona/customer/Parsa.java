package org.example.test.persona.customer;

import org.example.dto.CustomerData;
import org.example.dto.ImmutableCustomerData;
import org.example.test.data.model.TestCustomerData;

import java.util.UUID;

public class Parsa implements TestCustomerData {

  public static String IDENTIFIER = "428faf9c-e150-11ed-b5ea-0242ac120002";

  @Override
  public CustomerData customerData() {
    return ImmutableCustomerData
        .builder()
        .identifier(UUID.fromString(IDENTIFIER))
        .name("Parsa")
        .build();
  }
}
