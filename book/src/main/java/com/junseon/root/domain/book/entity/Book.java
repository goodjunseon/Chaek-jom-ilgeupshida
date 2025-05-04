package com.junseon.root.domain.book.entity;

import com.junseon.root.domain.author.entity.Author;
import com.junseon.root.domain.rental.entity.Rental;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private LocalDate publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "book")
    @Builder.Default
    private List<BookGenre> bookGenres = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @Builder.Default
    private List<Rental> rentals = new ArrayList<>();
}
