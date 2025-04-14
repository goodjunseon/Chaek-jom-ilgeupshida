package com.junseon.book.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserUpdateDTO {
    // 비밀번호, 이름, 전화번호 변경 가능
    private String name;
    private String password;
    private String phone;
}
