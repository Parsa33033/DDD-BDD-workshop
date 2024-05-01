package org.example.dto;

import java.util.UUID;
import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerData extends DataTransferObject {

  @Nullable
  UUID identifier();


  @Nullable
  String name();
}
