package com.monitoria.habibs.application.user.out;

import com.monitoria.habibs.domain.model.User;

public interface UserRepository {
    User save(User user);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
