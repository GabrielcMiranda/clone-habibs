package com.monitoria.habibs.infrastructure.user.rest.mapping;

import org.mapstruct.Mapper;

import com.monitoria.habibs.application.user.in.command.RegisterCommand;
import com.monitoria.habibs.application.user.in.output.UserOutput;
import com.monitoria.habibs.infrastructure.user.rest.dto.UserRequestDTO;
import com.monitoria.habibs.infrastructure.user.rest.dto.UserResponseDTO;

@Mapper(componentModel = "spring")
public interface UserMapperRest {
	RegisterCommand toRegisterCommand(UserRequestDTO request);

	UserResponseDTO toUserResponseDTO(UserOutput output);
}
