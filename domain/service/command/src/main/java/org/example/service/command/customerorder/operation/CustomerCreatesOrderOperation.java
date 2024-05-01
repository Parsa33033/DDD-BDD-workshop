package org.example.service.command.customerorder.operation;

import static org.example.framework.result.Result.checkPreviousFutureResult;

import java.util.concurrent.CompletableFuture;
import org.example.dto.OrderData;
import org.example.framework.error.Error;
import org.example.framework.error.OperationError;
import org.example.framework.interaction.OperationInteraction;
import org.example.framework.result.Result;
import org.example.incoming.service.order.CustomerOrderServiceCommand;
import org.example.incoming.service.order.CustomerOrderServiceError;
import org.example.model.config.error.OperationErrorCode;
import org.example.service.command.customerorder.domain.aggregate.CustomerOrder;
import org.example.service.command.customerorder.repository.CustomerOrderCommandRepositoryInteraction;

import javax.management.OperationsException;

public class CustomerCreatesOrderOperation implements
    OperationInteraction<CustomerOrderServiceCommand, CustomerCreatesOrderOperationData,
        CustomerOrderServiceError, OrderData> {

  private final CustomerOrderCommandRepositoryInteraction customerCommandRepositoryInteraction;

  public CustomerCreatesOrderOperation(CustomerOrderCommandRepositoryInteraction customerCommandRepositoryInteraction) {
    this.customerCommandRepositoryInteraction = customerCommandRepositoryInteraction;
  }

  @Override
  public CompletableFuture<Result<OrderData, CustomerOrderServiceError>> execute(final CustomerOrderServiceCommand command) {
    return validate(command)
        .thenCompose(checkPreviousFutureResult(this::read))
        .thenCompose(checkPreviousFutureResult(this::operation))
        .thenCompose(checkPreviousFutureResult(this::write))
        .thenApply(this::combineResult)
        .exceptionally(x -> Result.error(CustomerOrderServiceError.of(OperationErrorCode.STORAGE_NOT_FOUND)));
  }


  @Override
  public CompletableFuture<Result<CustomerCreatesOrderOperationData, CustomerOrderServiceError>> validate(
      final CustomerOrderServiceCommand command) {
    if (command == null || command.customerIdentifier() == null) {
      return CompletableFuture.completedFuture(Result.error(CustomerOrderServiceError.of(
          OperationError.OTHER)));
    }
    CustomerCreatesOrderOperationData data = new CustomerCreatesOrderOperationData();
    data.setCustomerIdentifier(command.customerIdentifier());
    return CompletableFuture.completedFuture(Result.ok(data));
  }

  @Override
  public CompletableFuture<Result<CustomerCreatesOrderOperationData, Error>> read(
      final CustomerCreatesOrderOperationData data) {
    return this.customerCommandRepositoryInteraction
        .read(data)
        .thenApply(result -> result.map(d -> data).mapError(e -> e));
  }

  @Override
  public CompletableFuture<Result<CustomerCreatesOrderOperationData, Error>> operation(
          final CustomerCreatesOrderOperationData data) {

    CustomerOrder aggregate = data.getCustomerOrder();
    return CompletableFuture.completedFuture(aggregate
        .createOrder()
        .map(result -> (CustomerCreatesOrderOperationData) data.setCustomerOrderChange(result))
        .mapError(e -> Error.of(e.code)));
  }

  @Override
  public CompletableFuture<Result<CustomerCreatesOrderOperationData, Error>> write(
      final CustomerCreatesOrderOperationData data) {
    return this.customerCommandRepositoryInteraction
        .write(data)
        .thenApply(result -> result.map(d -> data).mapError(e -> e));
  }

  @Override
  public Result<OrderData, CustomerOrderServiceError> combineResult(
      final Result<CustomerCreatesOrderOperationData, Error> result) {
    return result
        .map(r -> r.getCustomerOrderChange().customerOrderData().order())
        .mapError(e -> CustomerOrderServiceError.of(e.code()));
  }
}
