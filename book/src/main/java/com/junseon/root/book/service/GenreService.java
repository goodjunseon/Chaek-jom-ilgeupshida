package com.junseon.root.book.service;

import com.junseon.root.book.model.Genre;
import com.junseon.root.book.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> findById(Long genreId) {
        return genreRepository.findById(genreId);
    }
}
