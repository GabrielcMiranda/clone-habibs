package com.monitoria.habibs.application.user.in.inputPort;

import com.monitoria.habibs.application.user.in.command.UpdateUserCommand;
import com.monitoria.habibs.application.user.in.output.UserOutput;

public interface UpdateUserInputPort {
    UserOutput updateUser(UpdateUserCommand command);
}
