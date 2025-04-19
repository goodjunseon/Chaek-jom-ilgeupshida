package com.junseon.book.domain.dto.user;


import com.junseon.book.domain.entity.User;
import com.junseon.book.domain.enums.Role;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserFindAllDTO {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private Role role;



    public static UserFindAllDTO toDTO(User user) {
        UserFindAllDTO dto = new UserFindAllDTO();

        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRole(user.getRole());

        return dto;
    }
}
