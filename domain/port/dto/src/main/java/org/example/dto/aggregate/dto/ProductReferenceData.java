package org.example.dto.aggregate.dto;

import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Immutable
public interface ProductReferenceData extends DataTransferObject {

  @Nullable
  UUID identifier();
}
