package com.sec.security.model.dto.requests;

import lombok.Builder;

@Builder
public record AuthenticateRequest(String email, String password) {
}
