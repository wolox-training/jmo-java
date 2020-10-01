package com.wolox.training.repositories;

import com.wolox.training.model.User;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    Page<User> findByNameContainingIgnoreCaseAndBirthdateBetween(String name, LocalDate startDate,
        LocalDate endDate, Pageable pageable);

    @Query("select u from Usser u " +
        "where (:name is null or UPPER(u.name) like UPPER(:name)) " +
        "and (coalesce(:startDate, null) is null or coalesce(:endDate, null) is null " +
        "or u.birthdate between :startDate and :endDate) ")
    Page<User> findByOptinalNameAndBirthdate(
        @Param("name") String name,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable);

    @Query("select u from Usser u " +
        "where (:username is null or u.username = :username) " +
        "and (:name is null or u.name = :name) " +
        "and (:birthdate is null or u.birthdate = birthdate) ")
    Page<User> getAll(
        @Param("username") String username,
        @Param("name") String name,
        @Param("birthdate") LocalDate birthdate,
        Pageable pageable);
}
