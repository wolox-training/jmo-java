package com.wolox.training.repositories;

import com.wolox.training.model.Book;
import com.wolox.training.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    @Query("select u from Usser u " +
        "where UPPER(u.name) like UPPER(:name) " +
        "and u.birthdate between :startDate and :endDate " )
    List<User> findyByParameters(
        @Param("name") String name,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
}
