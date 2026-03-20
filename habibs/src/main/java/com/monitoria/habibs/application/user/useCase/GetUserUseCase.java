package com.monitoria.habibs.application.user.useCase;

import org.springframework.stereotype.Component;

import com.monitoria.habibs.application.user.in.inputPort.GetUserInputPort;
import com.monitoria.habibs.application.user.in.output.UserOutput;
import com.monitoria.habibs.application.user.mapping.UserMapper;
import com.monitoria.habibs.application.user.out.UserRepository;
import com.monitoria.habibs.domain.exception.UserNotFoundException;
import com.monitoria.habibs.domain.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetUserUseCase implements GetUserInputPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserOutput getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        return userMapper.toOutput(user);
    }
}
