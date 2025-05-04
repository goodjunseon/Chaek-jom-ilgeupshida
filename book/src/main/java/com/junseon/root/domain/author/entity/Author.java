package com.junseon.root.domain.author.entity;

import com.junseon.root.domain.book.entity.Book;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @Column(name = "name", unique = true)
    private String name;

    @Column(nullable = false)
    private String country;

    @OneToMany(mappedBy = "author")
    @Builder.Default
    private List<Book> books = new ArrayList<>();

}
