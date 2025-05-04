package com.junseon.root.domain.user.dto.response;


import com.junseon.root.domain.user.entity.User;
import com.junseon.root.global.enums.Role;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserListResponse {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private Role role;



    public static UserListResponse toDTO(User user) {
        UserListResponse dto = new UserListResponse();

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
