package com.sec.security.model.dto.response;

import lombok.Builder;

@Builder
public record UserDataResponse(String fullName, String role) {
}
