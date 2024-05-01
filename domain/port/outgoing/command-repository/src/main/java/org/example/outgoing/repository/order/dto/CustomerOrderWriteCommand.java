package org.example.outgoing.repository.order.dto;

import org.example.dto.aggregate.CustomerOrderData;
import org.example.framework.command.WriteCommand;
import org.example.outgoing.dto.change.CustomerOrderChange;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerOrderWriteCommand extends WriteCommand {

  @Nullable
  CustomerOrderData data();

  @Nullable
  CustomerOrderChange change();
}
