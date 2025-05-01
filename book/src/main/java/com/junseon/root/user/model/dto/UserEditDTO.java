package com.junseon.root.user.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEditDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
}
