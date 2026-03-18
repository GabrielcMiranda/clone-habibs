package com.monitoria.habibs.application.user.in.command;

public record CreateUserCommand(
    String name,
    String email,
    String password,
    String role
) {}
