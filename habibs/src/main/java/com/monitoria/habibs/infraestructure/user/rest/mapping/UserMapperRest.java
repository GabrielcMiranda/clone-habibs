package com.monitoria.habibs.infraestructure.user.rest.mapping;

import org.mapstruct.Mapper;

import com.monitoria.habibs.application.user.in.command.CreateUserCommand;
import com.monitoria.habibs.application.user.in.output.UserOutput;
import com.monitoria.habibs.infraestructure.user.rest.dto.UserRequestDTO;
import com.monitoria.habibs.infraestructure.user.rest.dto.UserResponseDTO;

@Mapper(componentModel = "spring")
public interface UserMapperRest {
    CreateUserCommand toCreateUserCommand(UserRequestDTO request);

	UserResponseDTO toUserResponseDTO(UserOutput output);
}
