package com.junseon.root.domain.user.dto.request;


import com.junseon.root.domain.user.entity.User;
import com.junseon.root.global.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupRequest {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private Role role = Role.USER;


    public static SignupRequest toUserSaveDTO(User user){
        SignupRequest signupRequest = new SignupRequest();

        signupRequest.setUserId(user.getUserId());
        signupRequest.setEmail(user.getEmail());
        signupRequest.setPassword(user.getPassword());
        signupRequest.setName(user.getName());
        signupRequest.setPhone(user.getPhone());
        signupRequest.setCreatedAt(user.getCreatedAt());

        return signupRequest;
    }

    public static User toUser(SignupRequest dto){
        User user = new User();

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setCreatedAt(dto.getCreatedAt());

        return user;
    }
}
