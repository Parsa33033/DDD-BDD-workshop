package org.example.service.command.customerorder.repository;

import java.util.concurrent.CompletableFuture;

import org.example.dto.aggregate.ImmutableCustomerOrderData;
import org.example.dto.OrderData;
import org.example.framework.error.Error;
import org.example.framework.interaction.RepositoryInteraction;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.outgoing.repository.order.OrderCommandRepository;
import org.example.outgoing.repository.order.dto.ImmutableCustomerOrderReadCommand;
import org.example.outgoing.repository.order.dto.ImmutableCustomerOrderWriteCommand;
import org.example.outgoing.repository.order.dto.CustomerOrderWriteCommand;
import org.example.service.command.customerorder.CustomerOrderCommandServiceInteractionData;
import org.example.service.command.customerorder.domain.aggregate.CustomerOrder;

public class CustomerOrderCommandRepositoryInteraction implements
        RepositoryInteraction<CustomerOrderCommandServiceInteractionData, Nothing, CustomerOrder> {

    private final OrderCommandRepository repository;

    public CustomerOrderCommandRepositoryInteraction(OrderCommandRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends CustomerOrderCommandServiceInteractionData> CompletableFuture<Result<T, Error>> read(
            final T data) {
        return repository
                .read(ImmutableCustomerOrderReadCommand
                        .builder()
                        .customerIdentifier(data.getCustomerIdentifier())
                        .build())
                .thenApply(result -> result
                        .map(d -> updateAggregateRoot(data, CustomerOrder.fromDataTransferObject(d)))
                        .mapError(e -> e));
    }

    @Override
    public <T extends CustomerOrderCommandServiceInteractionData> CompletableFuture<Result<Nothing,
            Error>> write(
            final T data) {
        CustomerOrderWriteCommand command = ImmutableCustomerOrderWriteCommand
                .builder()
                .data(data.getCustomerOrder().toDataTransferObject())
                .change(data.getCustomerOrderChange())
                .build();
        if (noChange(data)) {
            return CompletableFuture.completedFuture(Result.ok(Nothing.get()));
        }
        return repository
                .write(command)
                .thenApply(result -> result
                        .mapError(e -> Error.of(e.code)));
    }

    @Override
    public <T extends CustomerOrderCommandServiceInteractionData> T updateAggregateRoot(
            T data, CustomerOrder aggregateRoot) {
        data.setCustomerOrder(aggregateRoot);
        return data;
    }

    public <T extends CustomerOrderCommandServiceInteractionData> T newAggregateRoot(
            T data) {
        CustomerOrder aggregateRoot = CustomerOrder.fromDataTransferObject(
                ImmutableCustomerOrderData.builder().build());
        data.setCustomerOrder(aggregateRoot);
        return data;
    }

    @Override
    public <T extends CustomerOrderCommandServiceInteractionData> boolean noChange(final T data) {
        return data.getCustomerOrderChange() == null || data.getCustomerOrderChange().customerOrderData() == null
                || data.getCustomerOrder().equals(data.getCustomerOrderChange().customerOrderData());
    }

}
