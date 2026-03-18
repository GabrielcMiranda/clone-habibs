package com.monitoria.habibs.application.user.in.inputPort;

import com.monitoria.habibs.application.user.in.output.UserOutput;

public interface GetUserInputPort {
    UserOutput getUser(String email);
}
