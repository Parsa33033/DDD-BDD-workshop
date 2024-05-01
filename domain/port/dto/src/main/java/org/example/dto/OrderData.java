package org.example.dto;

import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

import java.util.Set;
import java.util.UUID;

@Immutable
public interface OrderData extends DataTransferObject {

  @Nullable
  UUID identifier();

  Set<ProductData> products();

  @Nullable
  UUID customerIdentifier();
}
