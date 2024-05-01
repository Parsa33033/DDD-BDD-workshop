package org.example.incoming.service.order;

import org.example.dto.OrderData;
import org.example.framework.command.Command;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Immutable
public interface CustomerOrderServiceCommand extends Command {

  @Nullable
  UUID customerIdentifier();
}
