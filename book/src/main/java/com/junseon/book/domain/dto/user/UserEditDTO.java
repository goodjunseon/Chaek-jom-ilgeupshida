package com.junseon.book.domain.dto.user;

import com.junseon.book.domain.enums.Role;
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
    private Role role;



}
