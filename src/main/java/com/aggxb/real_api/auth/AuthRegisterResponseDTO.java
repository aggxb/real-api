package com.aggxb.real_api.auth;

import java.util.UUID;

public record AuthRegisterResponseDTO(
        UUID id,
        String name,
        String username,
        String email
) {
}
