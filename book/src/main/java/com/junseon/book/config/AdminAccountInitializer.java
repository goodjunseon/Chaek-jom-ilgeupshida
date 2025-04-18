package com.junseon.book.config;

import com.junseon.book.domain.entity.User;
import com.junseon.book.domain.enums.Role;
import com.junseon.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AdminAccountInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args){
        if (userRepository.findByEmail("admin@book.com").isEmpty()) {
            User admin = User.builder()
                    .email("admin@book.com")
                    .password("admin1234")
                    .name("관리자")
                    .phone("010-1234-5678")
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("관리자 계정(admin@book.com) 생성 완료!");
        } else {
            System.out.println("관리자 계정(admin@book.com)은 이미 존재합니다.");
        }

    }
}
