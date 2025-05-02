package com.junseon.root.user.model.dto;

import com.junseon.root.user.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserMyPageDTO {

    private Long userId;
    private String email;
    private String name;
    private String password;
    private String phone;

    public static UserMyPageDTO from(User user){

        UserMyPageDTO dto = new UserMyPageDTO();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());

        return dto;
    }

}
