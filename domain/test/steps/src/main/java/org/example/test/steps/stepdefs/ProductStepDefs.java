package org.example.test.steps.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.example.test.data.config.TestContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.example.test.steps.stepdefs.datatable.mapper.ProductDataTable.toProductData;

public class ProductStepDefs {

    TestContext testContext;

    @Autowired
    public ProductStepDefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("Products:")
    public void givenCustomer(DataTable dataTable) {
        toProductData(dataTable).forEach(productData -> testContext.testData().addProduct(productData));
    }
}
