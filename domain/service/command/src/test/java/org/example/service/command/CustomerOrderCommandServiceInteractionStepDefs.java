package org.example.service.command;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.dto.OrderData;
import org.example.framework.result.Result;
import org.example.incoming.service.order.CustomerOrderCommandService;
import org.example.incoming.service.order.CustomerOrderServiceError;
import org.example.incoming.service.order.ImmutableCustomerOrderServiceCommand;
import org.example.service.command.customerorder.CustomerOrderCommandServiceInteraction;
import org.example.test.data.config.TestContext;
import org.example.test.data.config.TestData;
import org.example.test.mock.command.customerorder.OrderCommandRepositoryMockConfig;
import org.example.test.mock.config.ImmutableApplicationMockData;
import org.example.test.mock.config.MockData;
import org.example.test.mock.config.RepositoryOperation;
import org.example.test.mock.config.failure.TestError;
import org.example.test.mock.config.failure.TestException;
import org.example.test.mock.config.failure.TestFailure;
import org.example.test.persona.testdata.TestCustomer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.example.test.steps.stepdefs.datatable.mapper.ProductDataTable.toProductData;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerOrderCommandServiceInteractionStepDefs {

    TestContext testContext;

    private final OrderCommandRepositoryMockConfig orderCommandRepositoryMockConfig;

    private final CustomerOrderCommandService customerOrderCommandService;

    private Result<OrderData, CustomerOrderServiceError> result;

    @Autowired
    public CustomerOrderCommandServiceInteractionStepDefs(TestContext testContext) {
        this.testContext = testContext;
        orderCommandRepositoryMockConfig = new OrderCommandRepositoryMockConfig();
        customerOrderCommandService = new CustomerOrderCommandServiceInteraction(
                orderCommandRepositoryMockConfig.repository());
    }

    @When("customer {testCustomer} orders with cart")
    public void customerOrders(TestCustomer testCustomer)
            throws ExecutionException, InterruptedException {
        setupMockData();

        this.result = customerOrderCommandService
                .createOrder(ImmutableCustomerOrderServiceCommand
                        .builder()
                        .customerIdentifier(testCustomer.customerData().identifier())
                        .build())
                .get();

        this.testContext.result((Result) result);
    }

    @When("customer {testCustomer} order fails with error")
    public void customerOrderFailsWithError(TestCustomer testCustomer)
            throws ExecutionException, InterruptedException {
        Map<RepositoryOperation, TestError> failureMap = new HashMap<>();
        TestError testError = TestError.ERROR;
        failureMap.put(RepositoryOperation.CUSTOMER_ORDER_READ, testError);
        setupMockDataWithFailure(failureMap);

        this.result = customerOrderCommandService
                .createOrder(ImmutableCustomerOrderServiceCommand
                        .builder()
                        .customerIdentifier(testCustomer.customerData().identifier())
                        .build())
                .get();

        this.testContext.result((Result) result);
    }

    @When("customer {testCustomer} order fails with exception {testException}")
    public void customerOrderFailsWithException(TestCustomer testCustomer, TestException exception)
            throws ExecutionException, InterruptedException {
        Map<RepositoryOperation, TestException> failureMap = new HashMap<>();
        failureMap.put(RepositoryOperation.CUSTOMER_ORDER_READ, exception);
        setupMockDataWithFailure(failureMap);

        this.result = customerOrderCommandService
                .createOrder(ImmutableCustomerOrderServiceCommand
                        .builder()
                        .customerIdentifier(testCustomer.customerData().identifier())
                        .build())
                .get();

        this.testContext.result((Result) result);
    }

    @Then("expect result is error")
    public void resultIsError() {
        assertNotNull(result, "result should not be null");
        assertTrue(result.isError(), "result should be ok");
    }

    @Then("expect result is error {testException}")
    public void resultIsError(TestException testException) {
        assertNotNull(result, "result should not be null");
        assertTrue(result.isError(), "result should be ok");
        assertEquals(result.error().code, testException.name(), "test exception name and error code not equal");
    }

    @Then("expect the result is ok")
    public void resultIsOk() {
        assertNotNull(result, "result should not be null");
        assertTrue(result.isOk(), "result should be ok");
    }

    @Then("expect the result is unsuccessful with error {string}")
    public void resultIsError(String error) {
        assertNotNull(result, "result should not be null");
        assertTrue(result.isError(), "result should be error");
        assertEquals(result.error().code, error, "expected error: " + error + " but was :" + result.error().code);
    }

    @Then("expect order result contain Products:")
    public void orderWasReturnedForCustomer(DataTable dataTable) {
        toProductData(dataTable).stream().allMatch(expectedProduct ->
                result.object().products().stream().anyMatch(
                        actualProduct -> actualProduct.identifier().equals(expectedProduct.identifier())));
    }

    @Then("expect order was created for {testCustomer} with Products:")
    public void orderWasCreatedForCustomer(TestCustomer testCustomer, DataTable dataTable) {
        assertTrue(orderCommandRepositoryMockConfig
                .captureAllCommands().stream().anyMatch(
                        wc -> wc.change().customerOrderData().customerIdentifier().equals(
                                testCustomer.customerData().identifier())));
        assertTrue(orderCommandRepositoryMockConfig
                .captureAllCommands()
                .stream()
                .anyMatch(wc -> toProductData(dataTable).stream().allMatch(expectedProduct ->
                        wc.change().customerOrderData().order().products().stream().anyMatch(
                                actualProduct -> actualProduct.identifier().equals(expectedProduct.identifier())))));
    }

    private void setupMockData() {
        MockData<TestData> mockData = ImmutableApplicationMockData
                .builder()
                .data(testContext.testData())
                .failureMap(new HashMap<>())
                .build();
        orderCommandRepositoryMockConfig.setupMockData(mockData);
    }

    private void setupMockDataWithFailure(Map<RepositoryOperation, ? extends TestFailure> failureMap) {
        MockData<TestData> mockData = ImmutableApplicationMockData
                .builder()
                .data(testContext.testData())
                .failureMap(failureMap)
                .build();
        orderCommandRepositoryMockConfig.setupMockData(mockData);
    }
}
