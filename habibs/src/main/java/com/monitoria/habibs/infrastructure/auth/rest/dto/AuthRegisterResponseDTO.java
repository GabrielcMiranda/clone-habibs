package com.monitoria.habibs.infrastructure.auth.rest.dto;

public record AuthRegisterResponseDTO(
    String name,
    String email,
    String role
) {}
