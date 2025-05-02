package com.junseon.root.book.repository;

import com.junseon.root.book.model.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {

    @Modifying
    @Query("DELETE FROM BookGenre bg WHERE bg.book.id = :bookId")
    void deleteByBookId(@Param("bookId") Long bookId);
}
