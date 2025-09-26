package com.sec.security.model.dto.requests;

import lombok.Builder;

@Builder
public record ChangePasswordRequest(String oldPassword , String newPassword) {
}
