package com.monitoria.habibs.application.user.out;

import java.util.Optional;

import com.monitoria.habibs.domain.model.User;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
