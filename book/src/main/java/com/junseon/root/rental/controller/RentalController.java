package com.junseon.root.rental.controller;

import com.junseon.root.book.model.Book;
import com.junseon.root.book.service.BookService;
import com.junseon.root.rental.model.Rental;
import com.junseon.root.rental.model.dto.RentalDTO;
import com.junseon.root.rental.model.dto.RentalFormDTO;
import com.junseon.root.rental.service.RentalService;
import com.junseon.root.user.model.User;
import com.junseon.root.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RentalController {

    private final UserService userService;
    private final BookService bookService;
    private final RentalService rentalService;

    // 대여 폼을 보여주는 메서드
    @GetMapping("/user/rental/{userId}")
    public String showRentalForm(@PathVariable("userId") Long userId,
                                 @ModelAttribute("rentalForm") RentalFormDTO dto,
                                 Model model) {
        // 사용자 정보 확인
        User user = userService.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        // 대여 가능한 도서 목록을 가져오는 로직 구현
        List<Book> bookList = bookService.findAllBooks();
        model.addAttribute("user", user);
        model.addAttribute("bookList", bookList);

        return "rental/rentalForm"; // 대여 폼 뷰 이름
    }

    // 대여 요청을 처리하는 메서드
    @PostMapping("/user/rental/{userId}/{bookId}")
    public String createRental(@PathVariable("userId") Long userId,
                               @PathVariable("bookId") Long bookId,
                               @ModelAttribute("rentalForm") RentalFormDTO dto) {
        // 사용자 정보 확인
        User user = userService.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        // 도서 정보 확인
        Book book = bookService.findById(bookId);

        rentalService.rentBook(user, book);

        // 대여 요청 처리 로직 구현
        return "redirect:/user/rental/{userId}"; // 대여 후 이동할 페이지
    }

    @GetMapping("/user/rentals/{userId}")
    public String getUserRentalList(@PathVariable("userId") Long userId, Model model) {
        // 사용자 정보 확인
        User user = userService.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        // 대여 목록을 가져오는 로직 구현
        // 해당 사용자의 대여 정보 가져오기
        List<Rental> rentalList = rentalService.findByUserId(userId);

        List<RentalDTO> rentalDTOList = rentalList.stream().map(r -> new RentalDTO(
                r.getRentalId(),
                r.getBook().getTitle(),
                r.getRentalDate(),
                r.getDeadline(),
                r.getReturnDate(),
                r.getStatus()
        )).toList();

        model.addAttribute("user", user);
        model.addAttribute("rentalList", rentalList);

        return "rental/rentalList"; // 대여 목록 뷰 이름
    }

    @PostMapping("/user/rentals/{rentalId}/return")
    public String returnRental(@PathVariable("rentalId")  Long rentalId) {

        // rentalId로부터 userId를 조회하여 리다이렉트
        Rental rental = rentalService.findById(rentalId);
        Long userId = rental.getUser().getUserId();

        rentalService.returnBook(rentalId);

        return "redirect:/user/rentals/" +userId; // 반납 후 이동할 페이지
    }

}
