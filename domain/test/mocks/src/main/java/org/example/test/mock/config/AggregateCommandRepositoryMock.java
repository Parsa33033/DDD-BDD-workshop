package org.example.test.mock.config;


import java.util.List;
import java.util.UUID;
import org.example.framework.command.ReadCommand;
import org.example.framework.command.WriteCommand;
import org.example.framework.dto.DataTransferObject;
import org.example.framework.error.Error;
import org.example.framework.repository.Repository;
import org.example.framework.result.Result;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;

public interface AggregateCommandRepositoryMock<R extends Repository, RC extends ReadCommand,
    RR extends DataTransferObject, RE extends Error, WC extends WriteCommand,
    WR extends DataTransferObject, WE extends Error> extends
    RepositoryMock<R> {

  WC captureWriteCommand(UUID identifier);

  List<WC> captureAllWriteCommands();

  public void mockGetResult(
      UUID identifier, Result<RR, RE> result);

  public void mockGetResult(
      ArgumentMatcher<RC> matcher, Result<RR, RE> result);

  public void mockAnyGetResult(Result<RR, RE> result);

  public void mockAnyGetThrows(Throwable throwable);

  void mockAnyWriteResult(ArgumentMatcher<WC> matcher, Result<WR, WE> result);

  void mockAnyWriteResult(Result<WR, WE> result);
}
