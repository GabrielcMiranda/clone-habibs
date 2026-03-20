package com.monitoria.habibs.infrastructure.auth.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitoria.habibs.application.user.in.command.RegisterCommand;
import com.monitoria.habibs.application.user.in.command.LoginCommand;
import com.monitoria.habibs.application.user.in.inputPort.GetUserInputPort;
import com.monitoria.habibs.application.user.in.inputPort.LoginInputPort;
import com.monitoria.habibs.application.user.in.inputPort.RegisterInputPort;
import com.monitoria.habibs.application.user.in.output.TokenOutput;
import com.monitoria.habibs.application.user.in.output.UserOutput;
import com.monitoria.habibs.infrastructure.auth.rest.dto.AuthLoginRequestDTO;
import com.monitoria.habibs.infrastructure.auth.rest.dto.AuthLoginResponseDTO;
import com.monitoria.habibs.infrastructure.auth.rest.dto.AuthProfileResponseDTO;
import com.monitoria.habibs.infrastructure.auth.rest.dto.AuthRegisterRequestDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final LoginInputPort loginInputPort;
    private final RegisterInputPort registerInputPort;
    private final GetUserInputPort getUserInputPort;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO request) {
        TokenOutput output = loginInputPort.login(
                new LoginCommand(request.email(), request.password()));

        return ResponseEntity.ok(new AuthLoginResponseDTO(output.token()));
    }

    @PostMapping("/register")
        public ResponseEntity<AuthLoginResponseDTO> register(@RequestBody @Valid AuthRegisterRequestDTO request) {
        registerInputPort.register(
                new RegisterCommand(request.name(), request.email(), request.password(), request.role()));

        TokenOutput output = loginInputPort.login(
            new LoginCommand(request.email(), request.password()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthLoginResponseDTO(output.token()));
    }

    @GetMapping("/profile")
    public ResponseEntity<AuthProfileResponseDTO> profile(@AuthenticationPrincipal Jwt jwt) {
        UserOutput output = getUserInputPort.getUser(jwt.getSubject());

        AuthProfileResponseDTO response = new AuthProfileResponseDTO(
                output.name(),
                output.email(),
                output.role());

        return ResponseEntity.ok(response);
    }
}