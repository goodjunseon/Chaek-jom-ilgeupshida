package com.junseon.root.domain.author.service;

import com.junseon.root.domain.author.entity.Author;
import com.junseon.root.domain.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;


    @Transactional
    public Author findOrCreateAuthor(String authorName) {
        Optional<Author> optionalAuthor = authorRepository.findByName(authorName);

        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        } else {
            Author newAuthor = Author.builder()
                    .name(authorName)
                    .country("대한민국") // country 임시 값 ( 폼수정되면 수정)
                    .build();
            return authorRepository.save(newAuthor);
        }
    }


}
