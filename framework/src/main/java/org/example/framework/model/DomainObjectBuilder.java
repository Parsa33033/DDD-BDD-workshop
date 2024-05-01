package org.example.framework.model;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class DomainObjectBuilder<O, D> {

  protected D dto;

  public DomainObjectBuilder(D dto) {
    this.dto = dto;
  }

  protected abstract O build();

  public static <U, V> U toDomainObject(V value, Function<V, U> f) {
    if (value != null) {
      return f.apply(value);
    }
    return null;
  }

  public static <U, V> Set<U> toDomainObjectSet(Set<V> value, Function<V, U> f) {
    if (value != null) {
      return value.stream().map(f).collect(Collectors.toSet());
    }
    return null;
  }

  public static <U, V> V getDomainObject(U u, Function<U, V> f) {
    if (u != null) {
      return f.apply(u);
    }
    return null;
  }

  public static <U, V> Set<V> getDomainObjectSet(Set<U> u, Function<U, V> f) {
    if (u != null) {
      return u.stream().map(f).collect(Collectors.toSet());
    }
    return null;
  }
}
