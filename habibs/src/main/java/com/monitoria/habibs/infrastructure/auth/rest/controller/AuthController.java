package com.monitoria.habibs.infrastructure.auth.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitoria.habibs.application.user.in.command.LoginCommand;
import com.monitoria.habibs.application.user.in.inputPort.LoginInputPort;
import com.monitoria.habibs.application.user.in.output.LoginOutput;
import com.monitoria.habibs.infrastructure.auth.rest.dto.AuthLoginRequestDTO;
import com.monitoria.habibs.infrastructure.auth.rest.dto.AuthLoginResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final LoginInputPort loginInputPort;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO request) {
        LoginOutput output = loginInputPort.login(
                new LoginCommand(request.email(), request.password()));

        return ResponseEntity.ok(new AuthLoginResponseDTO(output.token()));
    }
}