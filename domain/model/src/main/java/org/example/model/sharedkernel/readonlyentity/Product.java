package org.example.model.sharedkernel.readonlyentity;

import org.example.dto.ImmutableProductData;
import org.example.dto.ProductData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.Identifier;
import org.example.model.sharedkernel.valueobject.ProductName;
import org.example.model.sharedkernel.valueobject.StockAmount;

import javax.validation.constraints.NotNull;

import static org.example.framework.model.DomainObjectBuilder.getDomainObject;

public class Product extends ReadOnlyEntity<Product, ProductData> {

    @NotNull
    private Identifier productIdentifier;

    @NotNull
    private ProductName productName;

    @NotNull
    private StockAmount stockAmount;


    public Product(ProductBuilder builder) {
        this.productIdentifier = builder.productIdentifier;
        this.productName = builder.productName;
        this.stockAmount = builder.stockAmount;
    }

    @Override
    public ProductData toDataTransferObject() {
        return ImmutableProductData.builder()
                .identifier(getDomainObject(productIdentifier, Identifier::value))
                .productName(getDomainObject(productName, ProductName::value))
                .stockAmount(getDomainObject(stockAmount, StockAmount::value))
                .build();
    }

    public static Product fromDataTransferObject(ProductData productData) {
        return new ProductBuilder(productData).build();
    }

    public static class ProductBuilder extends DomainObjectBuilder<Product, ProductData> {

        private Identifier productIdentifier;
        private ProductName productName;
        private StockAmount stockAmount;

        public ProductBuilder(ProductData productData) {
            super(productData);
        }

        @Override
        protected Product build() {
            this.productIdentifier = toDomainObject(this.dto.identifier(), Identifier::create);
            this.productName = toDomainObject(this.dto.productName(), ProductName::create);
            this.stockAmount = toDomainObject(this.dto.stockAmount(), StockAmount::create);
            return new Product(this);
        }
    }
}
