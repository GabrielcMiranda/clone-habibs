package com.monitoria.habibs.application.user.useCase;

import org.springframework.stereotype.Component;

import com.monitoria.habibs.application.user.in.command.CreateUserCommand;
import com.monitoria.habibs.application.user.in.inputPort.CreateUserInputPort;
import com.monitoria.habibs.application.user.in.output.UserOutput;
import com.monitoria.habibs.application.user.mapping.UserMapper;
import com.monitoria.habibs.application.user.out.UserRepository;
import com.monitoria.habibs.domain.enums.RoleUser;
import com.monitoria.habibs.domain.exception.EmailAlreadyExists;
import com.monitoria.habibs.domain.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserInputPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserOutput createUser(CreateUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExists("Email already in use");
        }
        
        User user = User.create(command.name(), command.email(), command.password(), Enum.valueOf(RoleUser.class, command.role()));

        userRepository.save(user);
        return userMapper.toOutput(user);
    }
}