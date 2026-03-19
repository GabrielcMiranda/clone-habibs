package com.monitoria.habibs.infrastructure.user.persistence.adapter;

import org.springframework.stereotype.Component;

import com.monitoria.habibs.application.user.out.UserRepository;
import com.monitoria.habibs.domain.model.User;
import com.monitoria.habibs.infrastructure.user.persistence.entity.UserEntity;
import com.monitoria.habibs.infrastructure.user.persistence.mapping.UserMapper;
import com.monitoria.habibs.infrastructure.user.persistence.repository.UserRepositoryJPA;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserRepositoryJPA userRepositoryJPA;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedEntity = userRepositoryJPA.save(userEntity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = userRepositoryJPA.findByEmail(email);
        return userMapper.toDomain(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepositoryJPA.existsByEmail(email);
    }

}
