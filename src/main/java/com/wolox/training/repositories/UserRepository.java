package com.wolox.training.repositories;

import com.wolox.training.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);
}
