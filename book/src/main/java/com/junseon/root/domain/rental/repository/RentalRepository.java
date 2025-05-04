package com.junseon.root.domain.rental.repository;

import com.junseon.root.domain.rental.entity.Rental;
import com.junseon.root.domain.rental.entity.RentalStatus;
import com.junseon.root.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUser(User user);

    @Query("SELECT r FROM Rental r JOIN FETCH r.book WHERE r.user = :user")
    List<Rental> findWithBookByUser(@Param("user") User user);

    List<Rental> findByUser_UserIdOrderByRentalDateDesc(Long userId);

    @Query("SELECT r FROM Rental r JOIN FETCH r.book WHERE r.user.id = :userId")
    List<Rental> findByUserId(@Param("userId") Long userId);

    List<Rental> findByUserAndStatusNot(User user, RentalStatus rentalStatus);
}
