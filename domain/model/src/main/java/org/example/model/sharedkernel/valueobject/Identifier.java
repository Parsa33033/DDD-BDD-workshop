package org.example.model.sharedkernel.valueobject;

import java.util.UUID;
import org.example.framework.model.SimpleValueObject;

public class Identifier implements SimpleValueObject<Identifier, UUID> {

  private UUID value;

  public Identifier(UUID value) {
    this.value = value;
  }

  @Override
  public UUID value() {
    return value;
  }

  public static Identifier create(final UUID uuid) {
    Identifier identifier = new Identifier(uuid);
    return identifier;
  }

}
