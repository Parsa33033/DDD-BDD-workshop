package org.example.service.command.customerorder.domain.object;

import org.example.dto.ProductData;
import org.example.dto.aggregate.dto.ImmutableProductReferenceData;
import org.example.dto.aggregate.dto.ProductReferenceData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.Identifier;
import org.example.model.sharedkernel.valueobject.ProductName;
import org.example.model.sharedkernel.valueobject.StockAmount;

import javax.validation.constraints.NotNull;

import static org.example.framework.model.DomainObjectBuilder.getDomainObject;

public class ProductReference extends ReadOnlyEntity<ProductReference, ProductReferenceData> {

    @NotNull
    private Identifier productIdentifier;


    public ProductReference(ProductReferenceBuilder builder) {
        this.productIdentifier = builder.productIdentifier;
    }

    @Override
    public ProductReferenceData toDataTransferObject() {
        return ImmutableProductReferenceData.builder()
                .identifier(getDomainObject(productIdentifier, Identifier::value))
                .build();
    }

    public static ProductReference fromDataTransferObject(ProductReferenceData referenceData) {
        return new ProductReferenceBuilder(referenceData).build();
    }

    public static class ProductReferenceBuilder extends DomainObjectBuilder<ProductReference, ProductReferenceData> {

        private Identifier productIdentifier;

        public ProductReferenceBuilder(ProductReferenceData productReferenceData) {
            super(productReferenceData);
        }

        @Override
        protected ProductReference build() {
            this.productIdentifier = toDomainObject(this.dto.identifier(), Identifier::create);
            return new ProductReference(this);
        }
    }
}
