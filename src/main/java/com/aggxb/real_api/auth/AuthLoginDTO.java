package com.aggxb.real_api.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginDTO(
        @NotBlank(message = "Insira um username ou email") String identifier,
        @NotBlank(message = "Insira uma senha") String password
) {
}
