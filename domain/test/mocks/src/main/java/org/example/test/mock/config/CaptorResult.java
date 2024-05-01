package org.example.test.mock.config;

import java.util.List;
import java.util.UUID;
import org.example.framework.command.WriteCommand;
import org.mockito.ArgumentCaptor;

public interface CaptorResult<C extends WriteCommand> {

  C captureCommand(UUID identifier);

  List<C> captureAllCommands();
}
