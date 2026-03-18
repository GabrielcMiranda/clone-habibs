package com.monitoria.habibs.application.user.in.inputPort;

import com.monitoria.habibs.application.user.in.command.CreateAuthUserCommand;
import com.monitoria.habibs.application.user.in.output.AuthUserOutput;

public interface CreateAuthUserInputPort {
    AuthUserOutput createAuthUser(CreateAuthUserCommand command);
}
