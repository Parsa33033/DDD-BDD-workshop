package org.example.service.command.customerorder;

import java.util.Optional;
import java.util.UUID;
import org.example.framework.dto.InteractionData;
import org.example.outgoing.dto.change.CustomerOrderChange;
import org.example.service.command.customerorder.domain.aggregate.CustomerOrder;

public class CustomerOrderCommandServiceInteractionData implements InteractionData {

  private UUID customerIdentifier;
  private CustomerOrder customerOrder;

  private CustomerOrderChange customerOrderChange;

  public UUID getCustomerIdentifier() {
    return customerIdentifier;
  }

  public CustomerOrderCommandServiceInteractionData setCustomerIdentifier(final UUID customerIdentifier) {
    this.customerIdentifier = customerIdentifier;
    return this;
  }

  public CustomerOrder getCustomerOrder() {
    return customerOrder;
  }

  public CustomerOrderCommandServiceInteractionData setCustomerOrder(final CustomerOrder customerOrder) {
    this.customerOrder = customerOrder;
    return this;
  }

  public CustomerOrderChange getCustomerOrderChange() {
    return customerOrderChange;
  }

  public CustomerOrderCommandServiceInteractionData setCustomerOrderChange(final CustomerOrderChange customerOrderChange) {
    this.customerOrderChange = customerOrderChange;
    return this;
  }
}
