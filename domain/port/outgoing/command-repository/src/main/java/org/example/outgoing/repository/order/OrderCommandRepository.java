package org.example.outgoing.repository.order;

import org.example.dto.aggregate.CustomerOrderData;
import org.example.framework.aggregate.AggregateCommandRepository;
import org.example.framework.result.Nothing;
import org.example.outgoing.repository.order.dto.CustomerOrderReadCommand;
import org.example.outgoing.repository.order.dto.CustomerOrderWriteCommand;
import org.example.outgoing.repository.order.error.CustomerOrderReadError;
import org.example.outgoing.repository.order.error.OrderWriteError;

public interface OrderCommandRepository extends
    AggregateCommandRepository<CustomerOrderData, Nothing, CustomerOrderReadError, OrderWriteError,
            CustomerOrderReadCommand, CustomerOrderWriteCommand> {

}
