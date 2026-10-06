package com.aggxb.real_api.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRegisterDTO(
        @NotBlank(message = "Insira um nome") String name,
        @NotBlank(message = "Insira um username") String username,
        @NotBlank(message = "Insira um email") @Email(message = "Insira um email válido") String email,
        @NotBlank(message = "Insira uma senha") @Size(min = 8, message = "A senha deve conter, no mínimo, 8 caracteres") String password
) {
}
