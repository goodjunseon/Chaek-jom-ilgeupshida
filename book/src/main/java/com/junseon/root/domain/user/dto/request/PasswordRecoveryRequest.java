package com.junseon.root.domain.user.dto.request;

import com.junseon.root.domain.user.entity.User;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PasswordRecoveryRequest {
    private String email;
    private String name;
    private String phone;

    public static PasswordRecoveryRequest toUserFindPassword(User user){
        PasswordRecoveryRequest passwordRecoveryRequest = new PasswordRecoveryRequest();

        passwordRecoveryRequest.setEmail(user.getEmail());
        passwordRecoveryRequest.setName(user.getName());
        passwordRecoveryRequest.setPhone(user.getPhone());
        return passwordRecoveryRequest;
    }

    public static User toUser(PasswordRecoveryRequest passwordRecoveryRequest){
        User user = new User();

        user.setEmail(passwordRecoveryRequest.getEmail());
        user.setName(passwordRecoveryRequest.getName());
        user.setPhone(passwordRecoveryRequest.getPhone());

        return user;
    }
}
