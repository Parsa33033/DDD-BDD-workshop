package org.example.model.sharedkernel.readonlyentity;

import org.example.dto.ImmutableOrderData;
import org.example.dto.OrderData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.Identifier;
import org.example.model.sharedkernel.valueobject.ProductName;

import javax.validation.constraints.NotNull;

import java.util.Set;

import static org.example.framework.model.DomainObjectBuilder.getDomainObject;
import static org.example.framework.model.DomainObjectBuilder.getDomainObjectSet;

public class Order extends ReadOnlyEntity<Order, OrderData> {

  @NotNull
  private Identifier orderIdentifier;

  @NotNull
  private Set<Product> products;

  @NotNull
  private Identifier customerIdentifier;

  public Order(OrderBuilder builder) {
    this.orderIdentifier = builder.orderIdentifier;
    this.products = builder.products;
    this.customerIdentifier = builder.customerIdentifier;
  }

  @Override
  public OrderData toDataTransferObject() {
    return ImmutableOrderData
        .builder()
        .identifier(getDomainObject(orderIdentifier, Identifier::value))
        .products(getDomainObjectSet(products, Product::toDataTransferObject))
        .customerIdentifier(getDomainObject(
                customerIdentifier,
                Identifier::value))
        .build();
  }

  public static Order fromDataTransferObject(OrderData orderData) {
    return new OrderBuilder(orderData).build();
  }

  public static class OrderBuilder extends DomainObjectBuilder<Order, OrderData> {


    private Identifier orderIdentifier;
    private Set<Product> products;
    private Identifier customerIdentifier;

    public OrderBuilder(OrderData orderData) {
      super(orderData);
    }

    @Override
    protected Order build() {
      this.orderIdentifier = toDomainObject(this.dto.identifier(), Identifier::create);
      this.products = toDomainObjectSet(this.dto.products(), Product::fromDataTransferObject);
      this.customerIdentifier = toDomainObject(this.dto.customerIdentifier(),
          Identifier::create);
      return new Order(this);
    }
  }
}
