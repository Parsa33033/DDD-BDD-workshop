package org.example.framework.interaction;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.example.framework.aggregate.AggregateRoot;
import org.example.framework.command.Command;
import org.example.framework.dto.DataTransferObject;
import org.example.framework.dto.InteractionData;
import org.example.framework.error.Error;
import org.example.framework.result.Result;

public interface OperationInteraction<C extends Command, D extends InteractionData,
    E extends Error, R extends DataTransferObject> {

  CompletableFuture<Result<R, E>> execute(C command);

  CompletableFuture<Result<D, E>> validate(C command);

  CompletableFuture<Result<D, Error>> read(D data);

  CompletableFuture<Result<D, Error>> operation(D data);

  CompletableFuture<Result<D, Error>> write(D data);

  Result<R, E> combineResult(Result<D, Error> result);
}
