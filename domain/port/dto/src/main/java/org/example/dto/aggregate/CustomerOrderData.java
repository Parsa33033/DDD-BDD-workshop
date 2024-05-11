package org.example.dto.aggregate;

import org.example.dto.OrderData;
import org.example.dto.ProductData;
import org.example.dto.aggregate.dto.CartProductIdentifiersData;
import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

import java.util.Set;
import java.util.UUID;

@Immutable
public interface CustomerOrderData extends DataTransferObject {

  @Nullable
  UUID customerIdentifier();

  @Nullable
  CartProductIdentifiersData cart();

  @Nullable
  OrderData order();

  Set<ProductData> products();
}
