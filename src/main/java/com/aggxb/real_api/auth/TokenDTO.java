package com.aggxb.real_api.auth;

public record TokenDTO(
        String token,
        Long expirationTime
) {
}
