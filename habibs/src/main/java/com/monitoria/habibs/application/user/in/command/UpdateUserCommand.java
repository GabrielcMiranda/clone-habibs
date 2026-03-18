package com.monitoria.habibs.application.user.in.command;

public record UpdateUserCommand(
    String currentEmail,
    String name,
    String email,
    String password,
    String role
) {}
