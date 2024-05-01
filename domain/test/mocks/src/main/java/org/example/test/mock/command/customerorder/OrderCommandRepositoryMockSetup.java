package org.example.test.mock.command.customerorder;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.example.dto.CartData;
import org.example.dto.CustomerData;
import org.example.dto.ProductData;
import org.example.dto.aggregate.CustomerOrderData;
import org.example.dto.aggregate.ImmutableCustomerOrderData;
import org.example.dto.aggregate.dto.ImmutableCartProductIdentifiersData;
import org.example.dto.aggregate.dto.ImmutableProductReferenceData;
import org.example.framework.error.ImmutableError;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.outgoing.repository.order.dto.CustomerOrderReadCommand;
import org.example.outgoing.repository.order.error.CustomerOrderReadError;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.MockData;
import org.example.test.mock.config.MockSetup;
import org.example.test.mock.config.RepositoryOperation;
import org.example.test.mock.config.failure.TestError;
import org.example.test.mock.config.failure.TestException;
import org.example.test.mock.config.failure.TestFailure;
import org.mockito.ArgumentMatcher;

public class OrderCommandRepositoryMockSetup implements MockSetup<TestData> {

    OrderCommandRepositoryMock repositoryMock;

    public OrderCommandRepositoryMockSetup(OrderCommandRepositoryMock repositoryMock) {
        this.repositoryMock = repositoryMock;
    }

    @Override
    public void setupMockData(final MockData<TestData> mockData) {
        Optional<TestFailure> failure = Optional.ofNullable(mockData
                .failureMap()
                .get(RepositoryOperation.CUSTOMER_ORDER_READ));
        if (failure.isEmpty()) {
            mockSuccess(mockData);
            CustomerNotFoundMatcher notFoundMatcher = new CustomerNotFoundMatcher(mockData);
            Result<CustomerOrderData, CustomerOrderReadError> result = Result.error(CustomerOrderReadError.of(
                    CustomerOrderReadError.CUSTOMER_NOT_FOUND));
            repositoryMock.mockGetResult(notFoundMatcher, result);
        } else {
            mockFailure(failure.get());
        }
    }

    @Override
    public void mockSuccess(MockData<TestData> mockData) {
        mockData.data().getCustomers().forEach(customer -> repositoryMock.mockGetResult(
                customer.identifier(),
                getResult(mockData, customer)
                ));
        repositoryMock.mockAnyWriteResult(Result.ok(Nothing.get()));
    }

    private Result<CustomerOrderData, CustomerOrderReadError> getResult(MockData<TestData> mockData, CustomerData customer) {
        return Result.ok(ImmutableCustomerOrderData
                .builder()
                .customerIdentifier(customer.identifier())
                .cart(ImmutableCartProductIdentifiersData.builder()
                        .identifier(mockData.data().getCartByCustomerId(customer.identifier()).map(CartData::identifier).orElse(null))
                        .productReferences(
                                mockData.data().getCartByCustomerId(customer.identifier()).map(CartData::products)
                                        .stream().flatMap(Set::stream)
                                        .map(ProductData::identifier)
                                        .map(i -> ImmutableProductReferenceData.builder()
                                                .identifier(i)
                                                .build()).collect(Collectors.toSet())
                        )
                        .build())
                .products(mockData.data().getCartByCustomerId(customer.identifier()).map(CartData::products).orElse(null))
                .build());
    }

    @Override
    public void mockFailure(TestFailure failure) {
        if (failure instanceof TestException) {
            repositoryMock.mockAnyGetThrows(((TestException) failure).exception());
        } else if (failure instanceof TestFailure) {
            repositoryMock.mockAnyGetResult(Result.error(CustomerOrderReadError.of(ImmutableError
                    .of(((TestError) failure).error().code())
                    .code())));
        }
    }

    private static class CustomerNotFoundMatcher implements ArgumentMatcher<CustomerOrderReadCommand> {

        MockData<TestData> testData;

        public CustomerNotFoundMatcher(MockData<TestData> testData) {
            this.testData = testData;
        }

        @Override
        public boolean matches(final CustomerOrderReadCommand customerOrderReadCommand) {
            return testData
                    .data()
                    .getCustomers()
                    .stream()
                    .noneMatch(customerData -> customerData
                            .identifier()
                            .equals(customerOrderReadCommand.customerIdentifier()));
        }
    }
}
