package com.monitoria.habibs.application.user.useCase;

import org.springframework.stereotype.Component;

import com.monitoria.habibs.application.user.in.command.UpdateUserCommand;
import com.monitoria.habibs.application.user.in.inputPort.UpdateUserInputPort;
import com.monitoria.habibs.application.user.in.output.UserOutput;
import com.monitoria.habibs.application.user.mapping.UserMapper;
import com.monitoria.habibs.application.user.out.UserRepository;
import com.monitoria.habibs.domain.enums.RoleUser;
import com.monitoria.habibs.domain.exception.EmailAlreadyExists;
import com.monitoria.habibs.domain.exception.UserNotFoundException;
import com.monitoria.habibs.domain.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateUserUseCase implements UpdateUserInputPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserOutput updateUser(UpdateUserCommand command) {
        User existingUser = userRepository.findByEmail(command.currentEmail());

        if (existingUser == null) {
            throw new UserNotFoundException("User not found with email: " + command.currentEmail());
        }

        String newEmail = command.email();

        if (!newEmail.equals(existingUser.getEmail()) && userRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyExists("Email already in use");
        }

        RoleUser role = Enum.valueOf(RoleUser.class, command.role());

        User updatedUser = User.builder()
                .name(command.name())
                .email(newEmail)
                .password(command.password())
                .role(role)
                .active(existingUser.isActive())
                .build();

        User savedUser = userRepository.save(updatedUser);

        return userMapper.toOutput(savedUser);
    }
}
