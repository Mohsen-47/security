package com.sec.security.service.api;

import com.sec.security.model.dto.requests.ChangePasswordRequest;
import com.sec.security.model.dto.response.UserDataResponse;

public interface UserService {
    String findUserName(String userName);

    UserDataResponse getUserData(String userName);

    void changePassword(ChangePasswordRequest request);
}
