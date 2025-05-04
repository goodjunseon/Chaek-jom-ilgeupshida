package com.junseon.root.domain.book.service;

import com.junseon.root.domain.book.entity.Book;
import com.junseon.root.domain.book.repository.BookGenreRepository;
import com.junseon.root.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookGenreRepository bookGenreRepository;

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAllWithAuthor();
    }

    @Transactional
    public void deleteById(Long bookId) {
        bookGenreRepository.deleteByBookId(bookId);
        bookRepository.deleteById(bookId);
    }

    @Transactional
    public void updateBookStock(Long bookId, int stock) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException( "Book not found"));
        if (book.getStock() < 0) {
            throw new IllegalArgumentException("재고 수량은 0 이상이어야 합니다.");
        }
        book.setStock(stock);
    }

    @Transactional
    public Book findById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(()-> new IllegalArgumentException("해당 도서가 존재하지 않습니다."));
    }
}
