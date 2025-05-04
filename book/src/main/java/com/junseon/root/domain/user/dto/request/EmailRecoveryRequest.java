package com.junseon.root.domain.user.dto.request;


import com.junseon.root.domain.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailRecoveryRequest {
    private String name;
    private String phone;



    public static EmailRecoveryRequest userFindEmailDTO(User user){
        EmailRecoveryRequest emailRecoveryRequest = new EmailRecoveryRequest();

        emailRecoveryRequest.setName(user.getName());
        emailRecoveryRequest.setPhone(user.getPhone());

        return emailRecoveryRequest;
    }

    public static User toUser(EmailRecoveryRequest emailRecoveryRequest){
        User user = new User();

        user.setName(emailRecoveryRequest.getName());
        user.setPhone(emailRecoveryRequest.getPhone());

        return user;
    }
}
