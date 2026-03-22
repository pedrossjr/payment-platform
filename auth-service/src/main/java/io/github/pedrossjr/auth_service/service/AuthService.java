package io.github.pedrossjr.auth_service.service;

import io.github.pedrossjr.auth_service.entity.User;
import io.github.pedrossjr.auth_service.repository.UserRepository;
import io.github.pedrossjr.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String register(String username, String password) {

        User user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        userRepository.save(user);

        return "Usuário criado com sucesso.";
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Senha inválida.");
        }

        return jwtService.generateToken(username);
    }
}
