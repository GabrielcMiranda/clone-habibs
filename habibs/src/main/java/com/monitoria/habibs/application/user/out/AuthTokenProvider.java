package com.monitoria.habibs.application.user.out;

import com.monitoria.habibs.domain.model.User;

public interface AuthTokenProvider {
    String generateAccessToken(User user);
}