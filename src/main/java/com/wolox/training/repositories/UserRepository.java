package com.wolox.training.repositories;

import com.wolox.training.model.Book;
import com.wolox.training.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    List<User> findByNameContainingIgnoreCaseAndBirthdateBetween(String name, LocalDate startDate,
        LocalDate endDate);

    List<User> findByNameContainingIgnoreCase(String name);
}
