package org.example.test.steps.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.example.dto.CartData;
import org.example.dto.ImmutableCartData;
import org.example.dto.ImmutableProductData;
import org.example.test.data.config.TestContext;
import org.example.test.persona.testdata.TestCustomer;
import org.example.test.persona.testdata.TestProduct;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.example.test.steps.stepdefs.datatable.mapper.ProductDataTable.toProductData;

public class CartStepDefs {

    TestContext testContext;

    @Autowired
    public CartStepDefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("{testCustomer} has a Cart with items:")
    public void customerHasCartWithItems(TestCustomer testCustomer, DataTable dataTable) {
        CartData cartData = ImmutableCartData.builder()
                .identifier(UUID.randomUUID())
                .customerIdentifier(testCustomer.customerData().identifier())
                .products(toProductData(dataTable))
                .build();
        testContext.testData().addCart(cartData);
    }

    @Given("Product {testProduct} is out of stock")
    public void productIsOutOfStock(TestProduct testProduct) {
        testContext.testData().updateProduct(ImmutableProductData.copyOf(testProduct.product()).withStockAmount(0));
    }
}
