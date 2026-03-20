package com.monitoria.habibs.infrastructure.auth.rest.dto;

import com.monitoria.habibs.domain.enums.RoleUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRegisterRequestDTO(
    @NotBlank(message = "Name is required")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "Password is required")
    String password,

    @NotNull(message = "Role is required")
    RoleUser role
) {}
