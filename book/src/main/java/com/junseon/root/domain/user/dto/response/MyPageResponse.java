package com.junseon.root.domain.user.dto.response;

import com.junseon.root.domain.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyPageResponse {

    private Long userId;
    private String email;
    private String name;
    private String password;
    private String phone;

    public static MyPageResponse from(User user){

        MyPageResponse dto = new MyPageResponse();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());

        return dto;
    }

}
