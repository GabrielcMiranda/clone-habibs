package com.monitoria.habibs.application.user.in.output;

public record UserOutput(
    String name,
    String email,
    String password,
    String role
) {}
