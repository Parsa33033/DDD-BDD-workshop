package org.example.service.command.customerorder.domain.object;

import org.example.dto.aggregate.dto.CartProductIdentifiersData;
import org.example.dto.aggregate.dto.ImmutableCartProductIdentifiersData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.Identifier;

import javax.validation.constraints.NotNull;
import java.util.Set;

import static org.example.framework.model.DomainObjectBuilder.getDomainObject;
import static org.example.framework.model.DomainObjectBuilder.getDomainObjectSet;

public class CartProductIdentifiers extends ReadOnlyEntity<CartProductIdentifiers, CartProductIdentifiersData> {

    @NotNull
    private Identifier cartIdentifier;

    @NotNull
    private Set<ProductReference> productReferences;


    public CartProductIdentifiers(CartProductBuilder builder) {
        this.cartIdentifier = builder.cartIdentifier;
        this.productReferences = builder.productReferences;
    }

    @Override
    public CartProductIdentifiersData toDataTransferObject() {
        return ImmutableCartProductIdentifiersData
                .builder()
                .identifier(getDomainObject(cartIdentifier, Identifier::value))
                .productReferences(getDomainObjectSet(productReferences, ProductReference::toDataTransferObject))
                .build();
    }

    public static CartProductIdentifiers fromDataTransferObject(CartProductIdentifiersData cartProductIdentifiersData) {
        return new CartProductBuilder(cartProductIdentifiersData).build();
    }

    public static class CartProductBuilder extends DomainObjectBuilder<CartProductIdentifiers, CartProductIdentifiersData> {


        private Identifier cartIdentifier;
        private Set<ProductReference> productReferences;
        private Identifier customerIdentifier;

        public CartProductBuilder(CartProductIdentifiersData cartProductIdentifiersData) {
            super(cartProductIdentifiersData);
        }

        @Override
        protected CartProductIdentifiers build() {
            this.cartIdentifier = toDomainObject(this.dto.identifier(), Identifier::create);
            this.productReferences = toDomainObjectSet(this.dto.productReferences(), ProductReference::fromDataTransferObject);
            return new CartProductIdentifiers(this);
        }
    }
}
