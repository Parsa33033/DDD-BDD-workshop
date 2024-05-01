package org.example.framework.result;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.example.framework.error.Error;

public final class Result<O, E extends Error> {

  private final O object;
  private final E error;

  private Result(O object, E error) {
    this.object = object;
    this.error = error;
  }

  public static <U, V extends Error> Result<U, V> of(U object, V error) {
    if (error != null) {
      return Result.error(error);
    } else {
      return Result.ok(object);
    }
  }

  public static <U, V extends Error> Result<U, V> ok(U object) {
    Objects.requireNonNull(object, "object should not be null");
    return new Result<>(object, null);
  }

  public static <U, V extends Error> Result<U, V> error(V error) {
    Objects.requireNonNull(error, "error should not be null");
    return new Result<>(null, error);
  }

  public boolean isOk() {
    return object != null;
  }

  public boolean isError() {
    return error != null;
  }

  public <U> Result<U, E> map(Function<O, U> mapper) {
    if (isOk()) {
      return Result.ok(mapper.apply(object));
    } else {
      return Result.error(error);
    }
  }

  public <V extends Error> Result<O, V> mapError(Function<E, V> mapper) {
    if (isError()) {
      return Result.error(mapper.apply(error));
    } else {
      return Result.ok(object);
    }
  }

  public static <C, D, E extends Error> Function<Result<D, E>,
      CompletableFuture<Result<D, Error>>> checkPreviousFutureResult(
      Function<D, CompletableFuture<Result<D, Error>>> func) {
    return prev -> prev.isOk()
        ? func.apply(prev.object)
        : CompletableFuture.completedFuture(Result.error(prev.error));
  }

  public O object() {
    return object;
  }

  public E error() {
    return error;
  }
}
