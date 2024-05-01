package org.example.test.persona.customer;

import java.util.UUID;
import org.example.dto.CustomerData;
import org.example.dto.ImmutableCustomerData;
import org.example.test.data.model.TestCustomerData;

public class JP implements TestCustomerData {

  public static String IDENTIFIER = "588faf9c-e150-11ed-b5ea-0242ac120002";

  @Override
  public CustomerData customerData() {
    return ImmutableCustomerData
        .builder()
        .identifier(UUID.fromString(IDENTIFIER))
        .name("Jean Pieter")
        .build();
  }
}
