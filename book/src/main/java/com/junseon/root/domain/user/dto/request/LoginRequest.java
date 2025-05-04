package com.junseon.root.domain.user.dto.request;


import com.junseon.root.domain.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest {

    private Long userId;
    private String email;
    private String password;


    public static LoginRequest toUserLoginDTO(User user){
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUserId(user.getUserId());
        loginRequest.setEmail(user.getEmail());
        loginRequest.setPassword(user.getPassword());

        return loginRequest;
    }

    public static User toUser(LoginRequest loginRequest){
        User user = new User();

        user.setUserId(user.getUserId());
        user.setEmail(loginRequest.getEmail());
        user.setPassword(loginRequest.getPassword());

        return user;
    }
}
