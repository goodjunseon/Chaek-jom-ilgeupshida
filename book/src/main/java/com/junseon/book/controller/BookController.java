package com.junseon.book.controller;

import com.junseon.book.domain.entity.Book;
import com.junseon.book.repository.BookRepository;
import com.junseon.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/list")
    public String showBookList(Model model){
        List<Book> bookList = bookService.findAllBooks();
        model.addAttribute("bookList", bookList);
        return "book/list";
    }
}
