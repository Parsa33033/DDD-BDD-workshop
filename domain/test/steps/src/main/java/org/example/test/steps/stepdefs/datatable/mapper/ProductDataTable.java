package org.example.test.steps.stepdefs.datatable.mapper;

import io.cucumber.datatable.DataTable;
import org.example.dto.ProductData;
import org.example.test.persona.testdata.TestProduct;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDataTable {

    private static final String COLUMN = "Products";

    public static Set<ProductData> toProductData(DataTable dataTable) {
        List<Map<String, String>> productMapList = dataTable.asMaps();
        Objects.requireNonNull(productMapList.get(0).get(COLUMN), "first row should contain 'Product'");
        return productMapList.stream().map(
                m -> TestProduct.valueOf(m.get(COLUMN)).product())
                .collect(Collectors.toSet());
    }
}
