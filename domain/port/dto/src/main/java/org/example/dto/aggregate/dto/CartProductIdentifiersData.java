package org.example.dto.aggregate.dto;

import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

import java.util.Set;
import java.util.UUID;

@Immutable
public interface CartProductIdentifiersData extends DataTransferObject {

  @Nullable
  UUID identifier();

  Set<ProductReferenceData> productReferences();
}
