package com.monitoria.habibs.infrastructure.user.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monitoria.habibs.infrastructure.user.persistence.entity.UserEntity;

@Repository
public interface UserRepositoryJPA extends JpaRepository<UserEntity, UUID>{
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
