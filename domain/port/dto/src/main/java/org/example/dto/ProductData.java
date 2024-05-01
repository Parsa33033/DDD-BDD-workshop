package org.example.dto;

import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Immutable
public interface ProductData extends DataTransferObject {

  @Nullable
  UUID identifier();

  String productName();

  int stockAmount();
}
