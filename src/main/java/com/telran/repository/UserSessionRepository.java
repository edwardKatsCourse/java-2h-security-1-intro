package com.telran.repository;

import com.telran.entity.UserSession;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSessionRepository extends MongoRepository<UserSession, String> {

    UserSession findByTokenAndIsValidTrue(String token);
}
