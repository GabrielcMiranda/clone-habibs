package com.monitoria.habibs.application.user.useCase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.monitoria.habibs.application.user.in.command.LoginCommand;
import com.monitoria.habibs.application.user.in.inputPort.LoginInputPort;
import com.monitoria.habibs.application.user.in.output.TokenOutput;
import com.monitoria.habibs.application.user.out.AuthTokenProvider;
import com.monitoria.habibs.application.user.out.UserRepository;
import com.monitoria.habibs.domain.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginUseCase implements LoginInputPort {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenProvider authTokenProvider;

    @Override
    public TokenOutput login(LoginCommand command) {
        User user = userRepository.findByEmail(command.email());

        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = authTokenProvider.generateAccessToken(user);
        return new TokenOutput(token);
    }
}