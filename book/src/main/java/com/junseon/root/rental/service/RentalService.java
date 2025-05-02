package com.junseon.root.rental.service;

import com.junseon.root.book.model.Book;
import com.junseon.root.book.repository.BookRepository;
import com.junseon.root.rental.model.Rental;
import com.junseon.root.rental.model.RentalStatus;
import com.junseon.root.rental.repository.RentalRepository;
import com.junseon.root.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final BookRepository bookRepository;
    private final RentalRepository rentalRepository;

    @Transactional
    public void rentBook(User user, Book book) {
        if (book.getStock() < 1) {
            throw new IllegalArgumentException("재고가 부족하여 대여할 수 없습니다.");
        }

        // 재고 차감 로직
        book.setStock(book.getStock() -1);
        bookRepository.save(book);

        Rental rental = Rental.builder()
                .user(user)
                .book(book)
                .rentalDate(LocalDate.now())
                .deadline(LocalDate.now().plusDays(14)) // 14일 후 반납 기한
                .status(RentalStatus.RENTED)
                .build();
        rentalRepository.save(rental);
    }

    public List<Rental> getRentalListByUser(User user) {
        return rentalRepository.findWithBookByUser(user);

    }

    @Transactional
    public void returnBook(Long rentalId) {
        System.out.println(">>> 반납 시도: rentalId = " + rentalId);

        Rental rental = rentalRepository.findById(rentalId).orElseThrow(
                () -> new IllegalArgumentException("해당 대여 정보가 존재하지 않습니다.")
        );

        System.out.println(">>> 상태 확인: " + rental.getStatus());

        if (rental.getReturnDate() != null) {
            throw new IllegalArgumentException("이미 반납 처리된 도서입니다.");
        }

        // returnDate 설정
        rental.setReturnDate(LocalDate.now());
        // 재고 증가 로직
        Book book = rental.getBook();
        book.setStock(book.getStock() + 1);
        // 대여 상태 변경
        rental.setStatus(RentalStatus.RETURNED);

    }

    public List<Rental> findByUserIdOrderByRentalDateDesc(Long userId) {
        return rentalRepository.findByUser_UserIdOrderByRentalDateDesc(userId);
    }

    public List<Rental> findByUserId(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

    public Rental findById(Long rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 대여 정보가 존재하지 않습니다."));
    }
}
