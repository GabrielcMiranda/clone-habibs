package com.monitoria.habibs.infrastructure.user.rest.dto;

public record UserResponseDTO(
    String name,
    String email,
    String role
) {}
