package com.junseon.root.domain.book.service;

import com.junseon.root.domain.book.entity.Book;
import com.junseon.root.domain.book.entity.BookGenre;
import com.junseon.root.domain.book.entity.Genre;
import com.junseon.root.domain.book.repository.BookGenreRepository;
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
