package org.example.outgoing.dto.change;

import org.example.dto.aggregate.CustomerOrderData;
import org.example.framework.repository.RepositoryChangeData;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerOrderChange extends RepositoryChangeData {

  @Nullable
  CustomerOrderData customerOrderData();
}
