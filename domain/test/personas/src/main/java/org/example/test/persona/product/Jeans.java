package org.example.test.persona.product;

import org.example.dto.ImmutableProductData;
import org.example.dto.ProductData;
import org.example.test.data.model.TestProductData;

import java.util.UUID;

public class Jeans implements TestProductData {

    public static String IDENTIFIER = "344faf9c-e150-11ed-b5ea-0242ac120008";

    @Override
    public ProductData product() {
        return ImmutableProductData
                .builder()
                .identifier(UUID.fromString(IDENTIFIER))
                .productName("Jeans")
                .stockAmount(2)
                .build();
    }
}
