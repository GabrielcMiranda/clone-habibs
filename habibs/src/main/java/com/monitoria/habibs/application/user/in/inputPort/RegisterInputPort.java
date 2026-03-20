package com.monitoria.habibs.application.user.in.inputPort;

import com.monitoria.habibs.application.user.in.command.RegisterCommand;
import com.monitoria.habibs.application.user.in.output.UserOutput;

public interface RegisterInputPort {
    UserOutput register(RegisterCommand command);
}
