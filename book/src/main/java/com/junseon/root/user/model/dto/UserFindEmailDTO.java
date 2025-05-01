package com.junseon.root.user.model.dto;


import com.junseon.root.user.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserFindEmailDTO {
    private String name;
    private String phone;



    public static UserFindEmailDTO userFindEmailDTO(User user){
        UserFindEmailDTO userFindEmailDTO = new UserFindEmailDTO();

        userFindEmailDTO.setName(user.getName());
        userFindEmailDTO.setPhone(user.getPhone());

        return userFindEmailDTO;
    }

    public static User toUser(UserFindEmailDTO userFindEmailDTO){
        User user = new User();

        user.setName(userFindEmailDTO.getName());
        user.setPhone(userFindEmailDTO.getPhone());

        return user;
    }
}
