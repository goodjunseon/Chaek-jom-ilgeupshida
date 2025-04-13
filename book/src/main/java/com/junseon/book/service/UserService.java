package com.junseon.book.service;

import com.junseon.book.domain.dto.LoginResultDTO;
import com.junseon.book.domain.dto.UserSaveDTO;
import com.junseon.book.domain.entity.User;
import com.junseon.book.domain.enums.LoginStatus;
import com.junseon.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(UserSaveDTO userSaveDTO) {
        User user = UserSaveDTO.toUser(userSaveDTO);
        userRepository.save(user);
    }

    public LoginResultDTO login(UserSaveDTO userSaveDTO) {
        Optional<User> byUserEmail = userRepository.findByEmail(userSaveDTO.getEmail());

        if (byUserEmail.isPresent()) {
            User user = byUserEmail.get();
            if (user.getPassword().equals(userSaveDTO.getPassword())) {
                return new LoginResultDTO(LoginStatus.SUCCESS, UserSaveDTO.toUserSaveDTO(user));
            } else {
                // 비밀번호 불일치
                return new LoginResultDTO(LoginStatus.PASSWORD_ERROR,null);
            }
        } else {
            // 이메일 불일치
            return new LoginResultDTO(LoginStatus.EMAIL_ERROR,null);
        }
    }
}
