package com.monitoria.habibs.application.user.in.inputPort;

import com.monitoria.habibs.application.user.in.command.LoginCommand;
import com.monitoria.habibs.application.user.in.output.LoginOutput;

public interface LoginInputPort {
    LoginOutput login(LoginCommand command);
}