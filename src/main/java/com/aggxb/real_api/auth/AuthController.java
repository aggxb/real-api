package com.aggxb.real_api.auth;

import com.aggxb.real_api.utils.ApiPaths;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(ApiPaths.AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthRegisterResponseDTO> register(
            @Valid @RequestBody AuthRegisterDTO authRegister
    ) {
        log.info("Criando usuário com username '{}'", authRegister.username());

        AuthRegisterResponseDTO createdUser = authService.register(authRegister);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(ApiPaths.USERS_PATH + "/{id}")
                .buildAndExpand(createdUser.id())
                .toUri();

        log.info("Usuário criado com sucesso!");

        return ResponseEntity.created(location).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(
            @Valid @RequestBody AuthLoginDTO authLogin
    ) {
        TokenDTO createdToken = authService.login(authLogin);

        log.info("Usuário com identificador '{}' entrou", authLogin.identifier());

        return ResponseEntity.ok(createdToken);
    }
}
