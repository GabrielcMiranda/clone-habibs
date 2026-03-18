package com.monitoria.habibs.domain.model;

import com.monitoria.habibs.domain.enums.RoleUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class User {
    private final String name;
    private final String email;
    private final String password;
    private final RoleUser role;
    private final boolean active;

    public static User create(String name, String email, String password, RoleUser role) {
        return new User(name, email, password, role, true);
    }
}
