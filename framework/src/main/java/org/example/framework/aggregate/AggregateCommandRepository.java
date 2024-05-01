package org.example.framework.aggregate;

import java.util.concurrent.CompletableFuture;
import org.example.framework.command.Command;
import org.example.framework.command.ReadCommand;
import org.example.framework.command.WriteCommand;
import org.example.framework.dto.DataTransferObject;
import org.example.framework.error.Error;
import org.example.framework.repository.CommandRepository;
import org.example.framework.result.Result;

public interface AggregateCommandRepository<RR extends DataTransferObject,
    WR extends DataTransferObject, RE extends Error, WE extends Error, RC extends ReadCommand,
    WC extends WriteCommand> extends CommandRepository {

  CompletableFuture<Result<RR, RE>> read(RC command);

  CompletableFuture<Result<WR, WE>> write(WC command);
}
