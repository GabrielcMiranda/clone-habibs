package com.monitoria.habibs.application.user.mapping;

import org.mapstruct.Mapper;

import com.monitoria.habibs.application.user.in.output.UserOutput;
import com.monitoria.habibs.domain.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserOutput toOutput(User user);
}
