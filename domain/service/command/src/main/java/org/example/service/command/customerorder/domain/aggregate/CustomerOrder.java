package org.example.service.command.customerorder.domain.aggregate;

import org.example.dto.ImmutableOrderData;
import org.example.dto.OrderData;
import org.example.dto.aggregate.CustomerOrderData;
import org.example.dto.aggregate.ImmutableCustomerOrderData;
import org.example.dto.aggregate.dto.CartProductIdentifiersData;
import org.example.dto.aggregate.dto.ImmutableCartProductIdentifiersData;
import org.example.framework.aggregate.AggregateRoot;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.result.Result;
import org.example.model.sharedkernel.readonlyentity.Order;
import org.example.model.sharedkernel.readonlyentity.Product;
import org.example.model.sharedkernel.valueobject.Identifier;
import org.example.outgoing.dto.change.CustomerOrderChange;
import org.example.outgoing.dto.change.ImmutableCustomerOrderChange;
import org.example.service.command.customerorder.domain.error.CustomerOrderError;
import org.example.service.command.customerorder.domain.object.CartProductIdentifiers;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if (products.stream().anyMatch(product -> product.toDataTransferObject().stockAmount() == 0)) {
            return Result.error(CustomerOrderError.of(CustomerOrderError.PRODUCT_OUT_OF_STOCK));
        }

        // create order
        OrderData orderData = ImmutableOrderData.builder()
                .identifier(UUID.randomUUID())
                .customerIdentifier(customerIdentifier.value())
                .products(products.stream().filter(
                                product -> cart.toDataTransferObject().productReferences().stream().anyMatch(
                                        pr -> Objects.equals(pr.identifier(), product.toDataTransferObject().identifier())))
                        .map(Product::toDataTransferObject)
                        .collect(Collectors.toSet()))
                .build();
        setOrder(orderData);

        // empty cart
        CartProductIdentifiersData cartProductIdentifiersData = cart.toDataTransferObject();
        setCart(ImmutableCartProductIdentifiersData.builder()
                .identifier(cartProductIdentifiersData.identifier())
                .productReferences(Set.of())
                .build());

        CustomerOrderChange change = ImmutableCustomerOrderChange.builder()
                .customerOrderData(this.toDataTransferObject())
                .build();

        return Result.ok(change);
    }


    private void setOrder(OrderData orderData) {
        order = Order.fromDataTransferObject(orderData);
    }

    private void setCart(CartProductIdentifiersData cartData) {
        cart = CartProductIdentifiers.fromDataTransferObject(cartData);
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
