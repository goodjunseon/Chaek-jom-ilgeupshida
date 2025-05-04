package com.junseon.root.domain.user.dto.request;

import com.junseon.root.domain.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileUpdateRequest {
    // 비밀번호, 이름, 전화번호 변경 가능
    private String name;
    private String password;
    private String phone;

    public static User toUser(ProfileUpdateRequest profileUpdateRequest){
        User user = new User();

        user.setPassword(profileUpdateRequest.getPassword());
        user.setName(profileUpdateRequest.getName());
        user.setPhone(profileUpdateRequest.getPhone());

        return user;
    }
}
