package com.aggxb.real_api.exception;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponseDTO(
        Instant timestamp,
        Integer status,
        String error,
        Map<String, String> message,
        String path
) {
}
