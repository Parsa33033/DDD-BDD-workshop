package org.example.service.command.customerorder.domain.aggregate;

import org.example.dto.aggregate.CustomerOrderData;
import org.example.dto.aggregate.ImmutableCustomerOrderData;
import org.example.framework.aggregate.AggregateRoot;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.result.Result;
import org.example.model.sharedkernel.readonlyentity.Order;
import org.example.model.sharedkernel.readonlyentity.Product;
import org.example.model.sharedkernel.valueobject.Identifier;
import org.example.outgoing.dto.change.CustomerOrderChange;
import org.example.service.command.customerorder.domain.error.CustomerOrderError;
import org.example.service.command.customerorder.domain.object.CartProductIdentifiers;

import javax.validation.constraints.NotNull;
import java.util.Set;

import static org.example.framework.model.DomainObjectBuilder.getDomainObject;
import static org.example.framework.model.DomainObjectBuilder.getDomainObjectSet;

public class CustomerOrder implements AggregateRoot<CustomerOrder, CustomerOrderData> {

    @NotNull
    private Identifier customerIdentifier;

    @NotNull
    private CartProductIdentifiers cart;

    private Order order;

    private Set<Product> products;

    public CustomerOrder(CustomerOrderBuilder builder) {
        this.customerIdentifier = builder.customerIdentifier;
        this.cart = builder.cart;
        this.order = builder.order;
        this.products = builder.products;
    }

    public Result<CustomerOrderChange, CustomerOrderError> createOrder() {
        // TODO create an order for the customer
        // when the order is created the cart should be empty and
        // product stockAmount should decrease by the amount of product ordered
        throw new RuntimeException("not implemented");
    }

    @Override
    public CustomerOrderData toDataTransferObject() {
        return ImmutableCustomerOrderData
                .builder()
                .customerIdentifier(getDomainObject(this.customerIdentifier, Identifier::value))
                .cart(getDomainObject(this.cart, CartProductIdentifiers::toDataTransferObject))
                .order(getDomainObject(this.order, Order::toDataTransferObject))
                .products(getDomainObjectSet(this.products, Product::toDataTransferObject))
                .build();
    }

    public static CustomerOrder fromDataTransferObject(CustomerOrderData customerOrderData) {
        return new CustomerOrderBuilder(customerOrderData).build();
    }

    private static class CustomerOrderBuilder extends
            DomainObjectBuilder<CustomerOrder, CustomerOrderData> {

        private Identifier customerIdentifier;
        private CartProductIdentifiers cart;
        private Order order;
        private Set<Product> products;

        public CustomerOrderBuilder(CustomerOrderData customerOrderData) {
            super(customerOrderData);
        }

        @Override
        protected CustomerOrder build() {
            this.customerIdentifier = toDomainObject(this.dto.customerIdentifier(), Identifier::create);
            this.cart = toDomainObject(this.dto.cart(), CartProductIdentifiers::fromDataTransferObject);
            this.order = toDomainObject(this.dto.order(), Order::fromDataTransferObject);
            this.products = toDomainObjectSet(this.dto.products(), Product::fromDataTransferObject);
            return new CustomerOrder(this);
        }
    }
}
