package com.aggxb.real_api.auth;

import com.aggxb.real_api.config.TokenProvider;
import com.aggxb.real_api.domain.roles.RoleEntity;
import com.aggxb.real_api.domain.roles.RoleRepository;
import com.aggxb.real_api.domain.roles.RoleType;
import com.aggxb.real_api.domain.user.UserEntity;
import com.aggxb.real_api.domain.user.UserRepository;
import com.aggxb.real_api.exception.EmailAlreadyExistsException;
import com.aggxb.real_api.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public AuthRegisterResponseDTO register(AuthRegisterDTO authRegister) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        boolean existsUserByUserName = userRepository.existsByUsername(authRegister.username());
        boolean existsUserByEmail = userRepository.existsByEmail(authRegister.email());

        if (existsUserByUserName)
            throw new UsernameAlreadyExistsException("Já existe um usuário cadastrado com o username '" + authRegister.username() + "'");

        if (existsUserByEmail)
            throw new EmailAlreadyExistsException("Já existe um usuário cadastrado com o email '" + authRegister.email() + "'");

        RoleEntity role = roleRepository.findByName(RoleType.ROLE_USER.name())
                .orElseThrow(() -> new IllegalStateException("Role não encontrada no banco de dados"));

        UserEntity createdUser = userRepository.save(
                UserEntity.builder()
                        .name(authRegister.name())
                        .username(authRegister.username())
                        .email(authRegister.email())
                        .password(passwordEncoder.encode(authRegister.password()))
                        .roles(Set.of(role))
                        .isActive(true)
                        .build()
        );

        return new AuthRegisterResponseDTO(
                createdUser.getId(),
                createdUser.getName(),
                createdUser.getUsername(),
                createdUser.getEmail()
        );
    }

    public TokenDTO login(AuthLoginDTO authLogin) throws BadCredentialsException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authLogin.identifier(), authLogin.password())
            );

            String token = tokenProvider.generateToken(authentication);

            return new TokenDTO(token, expirationTime);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("As credenciais informadas são inválidas");
        }
    }

}
