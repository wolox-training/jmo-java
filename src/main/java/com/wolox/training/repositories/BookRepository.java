package com.wolox.training.repositories;

import com.wolox.training.model.Book;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByAuthor(String author);

    Optional<Book> findByIsbn(String isbn);

    Page<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year,
        Pageable pageable);

    @Query("select b from Book b " +
        "where (:publisher is null or b.publisher = :publisher) " +
        "and (:genre is null or b.genre = :genre) " +
        "and (:year is null or b.year = :year) ")
    Page<Book> findByOptionalPublisherAndGenreAndYear(
        @Param("publisher") String publisher,
        @Param("genre") String genre,
        @Param("year") String year,
        Pageable pageable);
}
