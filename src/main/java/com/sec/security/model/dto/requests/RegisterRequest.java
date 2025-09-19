package com.sec.security.model.dto.requests;

import lombok.Builder;

@Builder
public record RegisterRequest(String  firstName, String lastName, String email, String password) {
}
