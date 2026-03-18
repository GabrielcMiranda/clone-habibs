package com.monitoria.habibs.infraestructure.user.rest.dto;

public record UserResponseDTO(
    String name,
    String email,
    String role
) {}
