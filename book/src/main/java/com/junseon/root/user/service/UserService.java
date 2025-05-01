package com.junseon.root.user.service;

import com.junseon.root.rental.model.Rental;
import com.junseon.root.rental.model.RentalStatus;
import com.junseon.root.rental.repository.RentalRepository;
import com.junseon.root.user.exception.UnreturnedBookExistsException;
import com.junseon.root.user.model.User;
import com.junseon.root.user.model.enums.LoginStatus;
import com.junseon.root.user.model.enums.Role;
import com.junseon.root.user.model.dto.*;
import com.junseon.root.user.repository.UserRepository;
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
    public void save(UserSaveDTO userSaveDTO) {
        User user = UserSaveDTO.toUser(userSaveDTO);
        userRepository.save(user);
    }

    public UserLoginResultDTO login(UserLoginDTO userSaveDTO) {
        Optional<User> byUserEmail = userRepository.findByEmailAndIsDeletedFalse(userSaveDTO.getEmail());

        if (byUserEmail.isPresent()) {
            User user = byUserEmail.get();
            if (user.getPassword().equals(userSaveDTO.getPassword())) {
                return new UserLoginResultDTO(LoginStatus.SUCCESS, UserLoginDTO.toUserLoginDTO(user));
            } else {
                // 비밀번호 불일치
                return new UserLoginResultDTO(LoginStatus.PASSWORD_ERROR,null);
            }
        } else {
            // 이메일 불일치
            return new UserLoginResultDTO(LoginStatus.EMAIL_ERROR,null);
        }
    }

    public String findEmail(UserFindEmailDTO userFindEmailDTO) {
        return userRepository.findByNameAndPhoneAndIsDeletedFalse(
                userFindEmailDTO.getName(),
                userFindEmailDTO.getPhone()
        ).map(User::getEmail).orElse(null);
    }

    public String findPassword(UserFindPasswordDTO userFindPasswordDTO) {
        return userRepository.findByEmailAndNameAndPhoneAndIsDeletedFalse(
                userFindPasswordDTO.getEmail(),
                userFindPasswordDTO.getName(),
                userFindPasswordDTO.getPhone()
        ).map(User::getPassword).orElse(null);
    }

    public boolean isEmailDuplicated(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User UserUpdateByUser(Long userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id=" + userId));

        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());

        return user;
    }

    public void UserUpdateByAdmin(Long userId, UserEditDTO dto) {
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

    public List<UserFindAllDTO> findAll(){
        List<User> userList = userRepository.findAll();
        List<UserFindAllDTO> userFindAllDTOList = new ArrayList<>();

        for (User user : userList) {
            userFindAllDTOList.add(UserFindAllDTO.toDTO(user));
        }
        return userFindAllDTOList;
    }

    public UserEditDTO findUserEditDTOById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    UserEditDTO dto = new UserEditDTO();
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

