package com.monitoria.habibs.application.user.in.inputPort;

import com.monitoria.habibs.application.user.in.command.CreateUserCommand;
import com.monitoria.habibs.application.user.in.output.UserOutput;

public interface CreateUserInputPort {
    UserOutput createUser(CreateUserCommand command);
}
