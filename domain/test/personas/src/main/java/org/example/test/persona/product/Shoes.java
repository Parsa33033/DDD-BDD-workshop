package org.example.test.persona.product;

import org.example.dto.ImmutableProductData;
import org.example.dto.ProductData;
import org.example.test.data.model.TestProductData;

import java.util.UUID;

public class Shoes implements TestProductData {

    public static String IDENTIFIER = "444faf9c-e150-11ed-b5ea-0242ac120002";

    @Override
    public ProductData product() {
        return ImmutableProductData
                .builder()
                .identifier(UUID.fromString(IDENTIFIER))
                .productName("Shoes")
                .stockAmount(3)
                .build();
    }
}
