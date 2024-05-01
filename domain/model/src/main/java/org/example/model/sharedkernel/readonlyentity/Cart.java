package org.example.model.sharedkernel.readonlyentity;

import org.example.dto.CartData;
import org.example.dto.ImmutableCartData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.Identifier;

import javax.validation.constraints.NotNull;
import java.util.Set;

import static org.example.framework.model.DomainObjectBuilder.getDomainObject;
import static org.example.framework.model.DomainObjectBuilder.getDomainObjectSet;

public class Cart extends ReadOnlyEntity<Cart, CartData> {

    @NotNull
    private Identifier cartIdentifier;

    @NotNull
    private Set<Product> products;

    @NotNull
    private Identifier customerIdentifier;

    public Cart(OrderBuilder builder) {
        this.cartIdentifier = builder.cartIdentifier;
        this.products = builder.products;
        this.customerIdentifier = builder.customerIdentifier;
    }

    @Override
    public CartData toDataTransferObject() {
        return ImmutableCartData
                .builder()
                .identifier(getDomainObject(cartIdentifier, Identifier::value))
                .products(getDomainObjectSet(products, Product::toDataTransferObject))
                .customerIdentifier(getDomainObject(
                        customerIdentifier,
                        Identifier::value))
                .build();
    }

    public static Cart fromDataTransferObject(CartData CartData) {
        return new OrderBuilder(CartData).build();
    }

    public static class OrderBuilder extends DomainObjectBuilder<Cart, CartData> {


        private Identifier cartIdentifier;
        private Set<Product> products;
        private Identifier customerIdentifier;

        public OrderBuilder(CartData CartData) {
            super(CartData);
        }

        @Override
        protected Cart build() {
            this.cartIdentifier = toDomainObject(this.dto.identifier(), Identifier::create);
            this.products = toDomainObjectSet(this.dto.products(), Product::fromDataTransferObject);
            this.customerIdentifier = toDomainObject(this.dto.customerIdentifier(),
                    Identifier::create);
            return new Cart(this);
        }
    }
}
