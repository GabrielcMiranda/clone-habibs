package com.monitoria.habibs.application.user.useCase;

import org.springframework.stereotype.Component;

import com.monitoria.habibs.application.user.in.command.CreateAuthUserCommand;
import com.monitoria.habibs.application.user.in.inputPort.CreateAuthUserInputPort;
import com.monitoria.habibs.application.user.in.output.AuthUserOutput;
import com.monitoria.habibs.application.user.out.UserRepository;
import com.monitoria.habibs.domain.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateAuthUserUseCase implements CreateAuthUserInputPort {
    private final UserRepository userRepository;

    @Override
    public AuthUserOutput createAuthUser(CreateAuthUserCommand command) {
        User user = userRepository.findByEmail(command.email());
        
        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if(!user.getPassword().equals(command.password())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = "";
        return new AuthUserOutput(token);
    }
    
}
