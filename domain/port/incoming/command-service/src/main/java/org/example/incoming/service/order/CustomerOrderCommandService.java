package org.example.incoming.service.order;

import java.util.concurrent.CompletableFuture;

import org.example.dto.OrderData;
import org.example.framework.result.Result;
import org.example.framework.service.CommandService;

public interface CustomerOrderCommandService extends CommandService {

  CompletableFuture<Result<OrderData, CustomerOrderServiceError>> createOrder(
      CustomerOrderServiceCommand command);
}
