package com.monitoria.habibs.application.user.in.command;

import com.monitoria.habibs.domain.enums.RoleUser;

public record RegisterCommand(
    String name,
    String email,
    String password,
    RoleUser role
) {}
