package com.wolox.training.repositories;

import com.wolox.training.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findByAuthor(String author);

}
