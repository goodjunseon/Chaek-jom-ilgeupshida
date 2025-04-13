package com.junseon.book.domain.dto;

import com.junseon.book.domain.enums.LoginStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultDTO {
    private LoginStatus status;
    private UserSaveDTO userSaveDTO;
}
