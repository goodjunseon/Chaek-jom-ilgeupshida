package com.junseon.book.domain.dto;


import com.junseon.book.domain.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSaveDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime createdAt;


    public static UserSaveDTO toUserSaveDTO(User user){
        UserSaveDTO userSaveDTO = new UserSaveDTO();

        userSaveDTO.setId(user.getId());
        userSaveDTO.setEmail(user.getEmail());
        userSaveDTO.setPassword(user.getPassword());
        userSaveDTO.setName(user.getName());
        userSaveDTO.setPhone(user.getPhone());
        userSaveDTO.setCreatedAt(user.getCreatedAt());

        return userSaveDTO;
    }

    public static User toUser(UserSaveDTO userSaveDTO){
        User user = new User();

        user.setEmail(userSaveDTO.getEmail());
        user.setPassword(userSaveDTO.getPassword());
        user.setName(userSaveDTO.getName());
        user.setPhone(userSaveDTO.getPhone());
        user.setCreatedAt(userSaveDTO.getCreatedAt());

        return user;
    }
}
