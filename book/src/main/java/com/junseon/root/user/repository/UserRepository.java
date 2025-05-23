package com.junseon.root.user.repository;

import com.junseon.root.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    Optional<User> findByNameAndPhoneAndIsDeletedFalse(String name, String phone);

    Optional<User> findByEmailAndNameAndPhoneAndIsDeletedFalse(String email, String name, String phone);

    boolean existsByEmail(String email);
}
