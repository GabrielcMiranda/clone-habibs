package com.monitoria.habibs.infraestructure.user.persistence.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.monitoria.habibs.domain.model.User;
import com.monitoria.habibs.infraestructure.user.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserEntity userEntity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserEntity toEntity(User user);
}
