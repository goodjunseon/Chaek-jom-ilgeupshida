package com.junseon.root.global.init;

import com.junseon.root.domain.author.entity.Author;
import com.junseon.root.domain.book.entity.Book;
import com.junseon.root.domain.book.entity.BookGenre;
import com.junseon.root.domain.book.entity.Genre;
import com.junseon.root.domain.author.repository.AuthorRepository;
import com.junseon.root.domain.book.repository.BookGenreRepository;
import com.junseon.root.domain.book.repository.BookRepository;
import com.junseon.root.domain.book.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BookDataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookGenreRepository bookGenreRepository;

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) { // 데이터가 없을 때만 삽입

            // 저자 저장
            Author author = Author.builder()
                    .name("김영한")
                    .country("대한민국")
                    .build();
            authorRepository.save(author);

            //장르 저장
            Genre backend = genreRepository.save(Genre.builder().genreName("Backend").build());
            Genre jpa = genreRepository.save(Genre.builder().genreName("JPA").build());
            Genre java = genreRepository.save(Genre.builder().genreName("Java").build());
            Genre https = genreRepository.save(Genre.builder().genreName("HTTPS").build());

            // 책 저장
            Book book1 = Book.builder()
                    .title("자바 ORM 표준 JPA 프로그래밍")
                    .isbn("9791161754277")
                    .stock(10)
                    .publisher("인프런")
                    .publishedAt(LocalDate.of(2020,12,5))
                    .author(author)
                    .build();
            bookRepository.save(book1);

            Book book2 = Book.builder()
                    .title("모든 개발자를 위한 HTTP 웹 기본 지식")
                    .isbn("9791161754278")
                    .stock(10)
                    .publisher("인프런")
                    .publishedAt(LocalDate.of(2023,6,23))
                    .author(author)
                    .build();
            bookRepository.save(book2);

            bookGenreRepository.saveAll(Arrays.asList(
                    BookGenre.builder().book(book1).genre(backend).build(),
                    BookGenre.builder().book(book1).genre(jpa).build(),
                    BookGenre.builder().book(book1).genre(java).build(),

                    BookGenre.builder().book(book2).genre(https).build(),
                    BookGenre.builder().book(book2).genre(backend).build()
            ));

            System.out.println("기본 책 데이터 삽입 완료");
        } else {
            System.out.println("책 데이터 이미 존재");
        }

    }
}
