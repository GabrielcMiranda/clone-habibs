package com.monitoria.habibs.application.user.in.command;

public record CreateAuthUserCommand(
    String email,
    String password
) {}
