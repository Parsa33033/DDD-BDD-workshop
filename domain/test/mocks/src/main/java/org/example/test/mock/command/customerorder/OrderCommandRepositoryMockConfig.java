package org.example.test.mock.command.customerorder;

import java.util.List;
import java.util.UUID;
import org.example.outgoing.repository.order.OrderCommandRepository;
import org.example.outgoing.repository.order.dto.CustomerOrderWriteCommand;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.CaptorResult;
import org.example.test.mock.config.MockConfig;
import org.example.test.mock.config.MockData;

public class OrderCommandRepositoryMockConfig implements
    MockConfig<TestData, OrderCommandRepository>, CaptorResult<CustomerOrderWriteCommand> {

  OrderCommandRepositoryMock orderCommandRepositoryMock;

  OrderCommandRepositoryMockSetup orderCommandRepositoryMockSetup;

  public OrderCommandRepositoryMockConfig() {
    orderCommandRepositoryMock = new OrderCommandRepositoryMock();
    orderCommandRepositoryMockSetup = new OrderCommandRepositoryMockSetup(
        orderCommandRepositoryMock);
  }

  @Override
  public OrderCommandRepository repository() {
    return orderCommandRepositoryMock.repository();
  }

  @Override
  public CustomerOrderWriteCommand captureCommand(UUID identifier) {
    return orderCommandRepositoryMock.captureWriteCommand(identifier);
  }

  public List<CustomerOrderWriteCommand> captureAllCommands() {
    return orderCommandRepositoryMock.captureAllWriteCommands();
  }

  @Override
  public void setupMockData(final MockData<TestData> mockData) {
    orderCommandRepositoryMockSetup.setupMockData(mockData);
  }
}
