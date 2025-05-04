package com.junseon.root.domain.user.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountEditRequest {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
}
