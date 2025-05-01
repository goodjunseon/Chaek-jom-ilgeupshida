package com.junseon.root.book.service;

import com.junseon.root.book.model.Book;
import com.junseon.root.book.model.BookGenre;
import com.junseon.root.book.model.Genre;
import com.junseon.root.book.repository.BookGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookGenreService {
    private final BookGenreRepository bookGenreRepository;

    public void save(Book book, Genre genre) {
        BookGenre bookGenre = BookGenre.builder()
                .book(book)
                .genre(genre)
                .build();
        bookGenreRepository.save(bookGenre);
    }
}
