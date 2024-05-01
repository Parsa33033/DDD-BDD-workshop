package org.example.test.mock.command.customerorder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.example.dto.aggregate.CustomerOrderData;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.outgoing.repository.order.OrderCommandRepository;
import org.example.outgoing.repository.order.dto.CustomerOrderReadCommand;
import org.example.outgoing.repository.order.dto.CustomerOrderWriteCommand;
import org.example.outgoing.repository.order.error.CustomerOrderReadError;
import org.example.outgoing.repository.order.error.OrderWriteError;
import org.example.test.mock.config.AggregateCommandRepositoryMock;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class OrderCommandRepositoryMock implements
    AggregateCommandRepositoryMock<OrderCommandRepository, CustomerOrderReadCommand, CustomerOrderData, CustomerOrderReadError, CustomerOrderWriteCommand, Nothing, OrderWriteError> {

  private final OrderCommandRepository orderCommandRepository;

  private final ArgumentCaptor<CustomerOrderWriteCommand> orderWriteCommandArgumentCaptor;

  public OrderCommandRepositoryMock() {
    orderCommandRepository = Mockito.mock(OrderCommandRepository.class);
    orderWriteCommandArgumentCaptor = ArgumentCaptor.forClass(CustomerOrderWriteCommand.class);
  }

  @Override
  public OrderCommandRepository repository() {
    return orderCommandRepository;
  }

  @Override
  public CustomerOrderWriteCommand captureWriteCommand(final UUID identifier) {
    Objects.requireNonNull(identifier, "identifier should not be null");
    return orderWriteCommandArgumentCaptor
        .getAllValues()
        .stream()
        .filter(wc -> wc.data() == null || identifier.equals(wc.data().customerIdentifier()))
        .findAny()
        .orElse(null);
  }

  @Override
  public List<CustomerOrderWriteCommand> captureAllWriteCommands() {
    return orderWriteCommandArgumentCaptor.getAllValues();
  }

  @Override
  public void mockGetResult(
      final UUID identifier, final Result<CustomerOrderData, CustomerOrderReadError> result) {
    Objects.requireNonNull(identifier, "identifier should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result))
        .when(orderCommandRepository)
        .read(argThat(new FindByUUIDMatcher(identifier)));
  }

  @Override
  public void mockGetResult(
      final ArgumentMatcher<CustomerOrderReadCommand> matcher,
      final Result<CustomerOrderData, CustomerOrderReadError> result) {
    Objects.requireNonNull(matcher, "matcher should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result))
        .when(orderCommandRepository)
        .read(argThat(matcher));
  }

  @Override
  public void mockAnyGetResult(final Result<CustomerOrderData, CustomerOrderReadError> result) {
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result)).when(orderCommandRepository).read(any());
  }

  @Override
  public void mockAnyGetThrows(final Throwable throwable) {
    doReturn(CompletableFuture.failedFuture(throwable))
        .when(orderCommandRepository)
        .read(any());
  }

  @Override
  public void mockAnyWriteResult(
      final ArgumentMatcher<CustomerOrderWriteCommand> matcher,
      final Result<Nothing, OrderWriteError> result) {
    Objects.requireNonNull(result, "result should not be null for write");
    doReturn(CompletableFuture.completedFuture(result))
        .when(orderCommandRepository)
        .write(any());
  }

  @Override
  public void mockAnyWriteResult(final Result<Nothing, OrderWriteError> result) {
    Objects.requireNonNull(result, "result should not be null for write");
    doReturn(CompletableFuture.completedFuture(result))
        .when(orderCommandRepository)
        .write(orderWriteCommandArgumentCaptor.capture());
  }

  private static class FindByUUIDMatcher implements ArgumentMatcher<CustomerOrderReadCommand> {

    private final UUID identifier;

    FindByUUIDMatcher(UUID uuid) {
      this.identifier = uuid;
    }

    @Override
    public boolean matches(final CustomerOrderReadCommand customerOrderReadCommand) {
      return customerOrderReadCommand.customerIdentifier() != null && customerOrderReadCommand
          .customerIdentifier()
          .equals(identifier);
    }
  }
}
