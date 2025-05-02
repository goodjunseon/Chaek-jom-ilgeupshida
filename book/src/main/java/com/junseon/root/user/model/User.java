package com.junseon.root.user.model;


import com.junseon.root.rental.model.Rental;
import com.junseon.root.user.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id // PK값
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String phone;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Role role = Role.USER; // 기본 디폴트 값은 USER

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; // 기본 디폴트 값은 false

    @OneToMany(mappedBy = "user") // 필드명 기준으로 가야함
    private List<Rental> rentals = new ArrayList<>(); // Array VS Linked 비교해보기


}
