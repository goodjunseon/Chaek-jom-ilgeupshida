package com.junseon.root.domain.author.controller;

import com.junseon.root.domain.author.service.AuthorService;
import com.junseon.root.domain.book.dto.request.BookCreateReq;
import com.junseon.root.domain.book.service.BookGenreService;
import com.junseon.root.domain.book.service.BookService;
import com.junseon.root.domain.book.service.GenreService;
import com.junseon.root.domain.user.dto.request.AccountEditRequest;
import com.junseon.root.domain.user.dto.response.UserListResponse;
import com.junseon.root.domain.author.entity.Author;
import com.junseon.root.domain.book.entity.Book;
import com.junseon.root.domain.book.entity.Genre;
import com.junseon.root.domain.user.entity.User;
import com.junseon.root.global.enums.Role;
import com.junseon.root.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BookService bookService;
    private final BookGenreService bookGenreService;
    private final GenreService genreService;
    private final AuthorService authorService;


    @GetMapping("/login")
    public String loginForm(){

        return"admin/login";
    }

    @PostMapping("/login")
    public String adminLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpSession session, Model model) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty() || !user.get().getPassword().equals(password) || user.get().getRole() != Role.ADMIN) {
            model.addAttribute("loginError", "관리자 인증 실패");
            return "admin/login";
        }

        session.setAttribute("adminUser", user.get()); // 세션 분리
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }


    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session) {
        User admin = (User) session.getAttribute("adminUser");

        if (admin == null || admin.getRole() != Role.ADMIN) {
            return "redirect:/admin/login";
        }

        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String adminUsersList(Model model){
        List<UserListResponse> userList = userService.findAll();

        model.addAttribute("userList",userList);
        return "admin/users";
    }

    // ✅ 사용자 수정
    @GetMapping("/users/{userId}/edit")
    public String userEditForm(@PathVariable("userId") Long userId, HttpSession session, Model model) {
        // 1. 세션에서 로그인 사용자 정보 가져와서 Role이 ADMIN인지 확인
        User admin = (User) session.getAttribute("adminUser");
        if (admin == null || admin.getRole() != Role.ADMIN) {
            return "redirect:/admin/login";
        }
        // 2. userId로 UserEditDTO 가져오기
        AccountEditRequest user = userService.findUserEditDTOById(userId);
        // 3. user가 null이면 에러 메시지 추가 후 사용자 목록으로 리다이렉트
        if (user == null) {
            System.out.println("user가 null인데?");
            model.addAttribute("errorMessage", "존재하지 않는 사용자입니다.");
            return "redirect:/admin/users";
        }
        // 4. user가 null이 아니면 모델에 담아서 사용자 수정 페이지로 이동
        model.addAttribute("user", user);
        return "admin/userEdit"; // → templates/admin/userEdit.html
    }

    // ✅ 사용자 수정
    @PostMapping("/users/{userId}/edit")
    public String userEditByAdmin(@PathVariable("userId") Long userId, @ModelAttribute AccountEditRequest accountEditRequest, HttpSession session, Model model) {

        // 관리자 검증
        User admin = (User) session.getAttribute("adminUser");
        if (admin == null || admin.getRole() != Role.ADMIN) {
            return "redirect:/admin/login";
        }

        // 사용자 수정
        userService.UserUpdateByAdmin(userId, accountEditRequest);
        // 수정 완료 후 사용자 목록으로 리다이렉트
        return "redirect:/admin/users";
    }


    @PostMapping("/users/{userId}/delete")
    public String deleteUserByAdmin(@PathVariable("userId") Long userId, HttpSession session ) {
        User admin = (User) session.getAttribute("adminUser");

        if (admin == null || admin.getRole() != Role.ADMIN) {
            return "redirect:/admin/login";
        }
        userService.UserDeleteByUser(userId);
        return "redirect:/admin/users";
    }


    // ✅책 추가
    @GetMapping("/books")
    public String editBooks(Model model) {
        List<Book> bookList = bookService.findAllBooks(); // 전체 책 리스트 가져오기
        model.addAttribute("bookList", bookList); // 모델에 담기

        return "admin/bookEdit";
    }

    @GetMapping("/book/create")
    public String createBookForm(Model model) {
        List<Genre> genres = genreService.findAllGenres();
        model.addAttribute("genres", genres);
        return "admin/bookCreate";
    }

    @PostMapping("/book/create")
    public String createBook(@ModelAttribute BookCreateReq bookCreateDTO) {
        // DTO -> Entity 변환
        Author author = authorService.findOrCreateAuthor(bookCreateDTO.getAuthorName());

        Book book = Book.builder()
                .title(bookCreateDTO.getTitle())
                .isbn(bookCreateDTO.getIsbn())
                .stock(bookCreateDTO.getStock())
                .publisher(bookCreateDTO.getPublisher())
                .publishedAt(LocalDate.parse(bookCreateDTO.getPublishedAt()))
                .author(author)
                .build();

        bookService.save(book); // Book 저장

        // 장르 매핑 저장
        if (bookCreateDTO.getGenreIds() != null) {
            for (Long genreId : bookCreateDTO.getGenreIds()) {
                Optional<Genre> genreOptional = genreService.findById(genreId);
                if (genreOptional.isPresent()) {
                    Genre genre = genreOptional.get();
                    bookGenreService.save(book, genre);
                }
            }
        }

        // 완료 후 도서 관리 페이지로 리다이렉트
        return "redirect:/admin/books";
    }

    // ✅ 책 삭제
    @PostMapping("/book/{bookId}/delete")
    public String bookDelete(@PathVariable("bookId") Long bookId){
        bookService.deleteById(bookId);
        return "redirect:/admin/books"; // 삭제 후 리다이렉트
    }

    // ✅ 책 재고 수정
    @PostMapping("/book/{bookId}/update-stock")
    public String updateBookStock(@PathVariable("bookId") Long bookId, @RequestParam("stock") int stock){

        bookService.updateBookStock(bookId,stock);
        return "redirect:/admin/books"; // 수정 후 리다이렉트

    }


}
