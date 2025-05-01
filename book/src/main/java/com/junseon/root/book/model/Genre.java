package com.junseon.root.book.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    @Column(nullable = false, unique = true, name = "genre_name")
    private String genreName;

    @Column
    private String description;

    @OneToMany(mappedBy = "genre")
    private List<BookGenre> bookGenres = new ArrayList<>();

}
