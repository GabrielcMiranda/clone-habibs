package com.monitoria.habibs.application.user.in.inputPort;

import com.monitoria.habibs.application.user.in.command.LoginCommand;
import com.monitoria.habibs.application.user.in.output.TokenOutput;

public interface LoginInputPort {
    TokenOutput login(LoginCommand command);
}