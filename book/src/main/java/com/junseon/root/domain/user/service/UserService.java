package com.junseon.root.domain.user.service;

import com.junseon.root.domain.rental.entity.Rental;
import com.junseon.root.domain.rental.entity.RentalStatus;
import com.junseon.root.domain.rental.repository.RentalRepository;
import com.junseon.root.domain.user.dto.request.*;
import com.junseon.root.domain.user.dto.response.UserListResponse;
import com.junseon.root.domain.user.dto.response.LoginResponse;
import com.junseon.root.global.exception.UnreturnedBookExistsException;
/*import com.junseon.root.user.dto.*;*/
import com.junseon.root.domain.user.entity.User;
import com.junseon.root.global.enums.LoginStatus;
import com.junseon.root.global.enums.Role;
import com.junseon.root.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Transactional
    public void save(SignupRequest signupRequest) {
        User user = SignupRequest.toUser(signupRequest);
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest userSaveDTO) {
        Optional<User> byUserEmail = userRepository.findByEmailAndIsDeletedFalse(userSaveDTO.getEmail());

        if (byUserEmail.isPresent()) {
            User user = byUserEmail.get();
            if (user.getPassword().equals(userSaveDTO.getPassword())) {
                return new LoginResponse(LoginStatus.SUCCESS, LoginRequest.toUserLoginDTO(user));
            } else {
                // 비밀번호 불일치
                return new LoginResponse(LoginStatus.PASSWORD_ERROR,null);
            }
        } else {
            // 이메일 불일치
            return new LoginResponse(LoginStatus.EMAIL_ERROR,null);
        }
    }

    public String findEmail(EmailRecoveryRequest emailRecoveryRequest) {
        return userRepository.findByNameAndPhoneAndIsDeletedFalse(
                emailRecoveryRequest.getName(),
                emailRecoveryRequest.getPhone()
        ).map(User::getEmail).orElse(null);
    }

    public String findPassword(PasswordRecoveryRequest passwordRecoveryRequest) {
        return userRepository.findByEmailAndNameAndPhoneAndIsDeletedFalse(
                passwordRecoveryRequest.getEmail(),
                passwordRecoveryRequest.getName(),
                passwordRecoveryRequest.getPhone()
        ).map(User::getPassword).orElse(null);
    }

    public boolean isEmailDuplicated(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User UserUpdateByUser(Long userId, ProfileUpdateRequest dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id=" + userId));

        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());

        return user;
    }

    public void UserUpdateByAdmin(Long userId, AccountEditRequest dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id=" + userId));
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setRole(Role.valueOf(dto.getRole()));

        userRepository.save(user);


    }

    @Transactional
    public void UserDeleteByUser(Long userId) {
        User user = userRepository.findById(userId)
                        .orElseThrow(()-> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id=" + userId));

        // 반납하지 않은 도서가 있으면 탈퇴 불가
        List<Rental> activeRentals = rentalRepository.findByUserAndStatusNot(user, RentalStatus.RETURNED);
        if (!activeRentals.isEmpty()) {
            throw new UnreturnedBookExistsException(userId, "반납하지 않은 도서가 있어 탈퇴할 수 없습니다.");
        }

        // 탈퇴 진행
        user.setDeleted(true);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email);
    }

    public List<UserListResponse> findAll(){
        List<User> userList = userRepository.findAll();
        List<UserListResponse> userListResponseList = new ArrayList<>();

        for (User user : userList) {
            userListResponseList.add(UserListResponse.toDTO(user));
        }
        return userListResponseList;
    }

    public AccountEditRequest findUserEditDTOById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    AccountEditRequest dto = new AccountEditRequest();
                    dto.setUserId(user.getUserId());
                    dto.setEmail(user.getEmail());
                    dto.setPassword(user.getPassword());
                    dto.setPhone(user.getPhone());
                    dto.setName(user.getName());
                    dto.setRole(user.getRole().name());
                    return dto;
                }).orElse(null);
    }
}

