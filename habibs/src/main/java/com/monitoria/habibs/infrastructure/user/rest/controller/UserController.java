package com.monitoria.habibs.infraestructure.user.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitoria.habibs.application.user.in.command.UpdateUserCommand;
import com.monitoria.habibs.application.user.in.inputPort.CreateUserInputPort;
import com.monitoria.habibs.application.user.in.inputPort.GetUserInputPort;
import com.monitoria.habibs.application.user.in.inputPort.UpdateUserInputPort;
import com.monitoria.habibs.application.user.in.output.UserOutput;
import com.monitoria.habibs.infraestructure.user.rest.dto.UserRequestDTO;
import com.monitoria.habibs.infraestructure.user.rest.dto.UserResponseDTO;
import com.monitoria.habibs.infraestructure.user.rest.mapping.UserMapperRest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final CreateUserInputPort createUserInputPort;
    private final GetUserInputPort getUserInputPort;
    private final UpdateUserInputPort updateUserInputPort;
	private final UserMapperRest userMapperRest;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO request) {
	UserOutput output = createUserInputPort.createUser(
		userMapperRest.toCreateUserCommand(request));

	UserResponseDTO response = userMapperRest.toUserResponseDTO(output);

	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String email) {
	UserOutput output = getUserInputPort.getUser(email);

	UserResponseDTO response = userMapperRest.toUserResponseDTO(output);

	return ResponseEntity.ok(response);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String email,
						      @RequestBody @Valid UserRequestDTO request) {
	UpdateUserCommand command = new UpdateUserCommand(
		email,
		request.name(),
		request.email(),
		request.password(),
		request.role());

	UserOutput output = updateUserInputPort.updateUser(command);

	UserResponseDTO response = userMapperRest.toUserResponseDTO(output);

	return ResponseEntity.ok(response);
    }
}

