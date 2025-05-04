package com.junseon.root.domain.user.dto.response;

import com.junseon.root.domain.user.dto.request.LoginRequest;
import com.junseon.root.global.enums.LoginStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private LoginStatus status;
    private LoginRequest loginRequest;
}
