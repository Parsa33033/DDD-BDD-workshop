package org.example.model.sharedkernel.readonlyentity;

import org.example.dto.CustomerData;
import org.example.dto.ImmutableCustomerData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.CustomerName;
import org.example.model.sharedkernel.valueobject.Identifier;

import javax.validation.constraints.NotNull;

import static org.example.framework.model.DomainObjectBuilder.getDomainObject;

public class Customer extends ReadOnlyEntity<Customer, CustomerData> {

  @NotNull
  private Identifier customerIdentifier;

  @NotNull
  private CustomerName customerName;


  public Customer(CustomerBuilder builder) {
    this.customerIdentifier = builder.customerIdentifier;
    this.customerName = builder.customerName;
  }

  @Override
  public CustomerData toDataTransferObject() {
    return ImmutableCustomerData.builder()
        .identifier(getDomainObject(customerIdentifier, Identifier::value))
        .name(getDomainObject(customerName, CustomerName::value))
        .build();
  }

  public static Customer fromDataTransferObject(CustomerData customerData) {
    return new CustomerBuilder(customerData).build();
  }

  public static class CustomerBuilder extends DomainObjectBuilder<Customer, CustomerData> {

    Identifier customerIdentifier;

    CustomerName customerName;

    public CustomerBuilder(CustomerData customerData) {
      super(customerData);
    }

    @Override
    protected Customer build() {
      this.customerIdentifier = toDomainObject(this.dto.identifier(), Identifier::create);
      this.customerName = toDomainObject(this.dto.name(), CustomerName::create);
      return new Customer(this);
    }
  }
}
