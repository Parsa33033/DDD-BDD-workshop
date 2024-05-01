package org.example.test.mock.config;

public enum RepositoryOperation {

  CUSTOMER_ORDER_READ("CUSTOMER_GET_BY_UUID"),
  ORDER_GET_BY_UUID("ORDER_GET_BY_UUID");

  String value;

  RepositoryOperation(String value) {
    this.value = value;
  }

}
