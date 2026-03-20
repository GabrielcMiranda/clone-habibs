package com.monitoria.habibs.application.user.in.command;

public record RegisterCommand(
    String name,
    String email,
    String password,
    String role
) {}
