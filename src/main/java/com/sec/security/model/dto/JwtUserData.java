package com.sec.security.model.dto;

import lombok.Builder;

@Builder
public record JwtUserData(String username) {
}
