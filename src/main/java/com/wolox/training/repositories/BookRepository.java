package com.wolox.training.repositories;

import com.wolox.training.model.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b.author from Book b where b.author :author")
    Optional<Book> findBookByAuthor(@Param("author") String author);
}
