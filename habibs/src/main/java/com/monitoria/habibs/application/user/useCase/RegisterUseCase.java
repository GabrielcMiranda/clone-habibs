package com.monitoria.habibs.application.user.useCase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.monitoria.habibs.application.user.in.command.RegisterCommand;
import com.monitoria.habibs.application.user.in.inputPort.RegisterInputPort;
import com.monitoria.habibs.application.user.in.output.UserOutput;
import com.monitoria.habibs.application.user.mapping.UserMapper;
import com.monitoria.habibs.application.user.out.UserRepository;
import com.monitoria.habibs.domain.enums.RoleUser;
import com.monitoria.habibs.domain.exception.EmailAlreadyExists;
import com.monitoria.habibs.domain.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegisterUseCase implements RegisterInputPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserOutput register(RegisterCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExists("Email already in use");
        }

        String hashedPassword = passwordEncoder.encode(command.password());
        User user = User.create(command.name(), command.email(), hashedPassword, Enum.valueOf(RoleUser.class, command.role()));

        userRepository.save(user);
        return userMapper.toOutput(user);
    }
}