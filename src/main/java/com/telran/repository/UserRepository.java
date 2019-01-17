package com.telran.repository;

import com.telran.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
}
