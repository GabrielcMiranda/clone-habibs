package com.monitoria.habibs.application.user.in.command;

public record LoginCommand(
    String email,
    String password
) {}