package com.monitoria.habibs.infrastructure.user.persistence.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.monitoria.habibs.domain.model.User;
import com.monitoria.habibs.infrastructure.user.persistence.entity.UserEntity;

@Mapper(componentModel = "spring", implementationName = "PersistenceUserMapperImpl")
public interface UserMapper {
    User toDomain(UserEntity userEntity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserEntity toEntity(User user);
}
