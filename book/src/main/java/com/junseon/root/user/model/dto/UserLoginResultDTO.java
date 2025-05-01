package com.junseon.root.user.model.dto;

import com.junseon.root.user.model.enums.LoginStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResultDTO {
    private LoginStatus status;
    private UserLoginDTO userLoginDTO;
}
