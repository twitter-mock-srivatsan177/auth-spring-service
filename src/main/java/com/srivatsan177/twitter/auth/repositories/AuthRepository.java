package com.srivatsan177.twitter.auth.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.srivatsan177.twitter.auth.models.entity.Auth;

public interface AuthRepository extends MongoRepository<Auth, String> {

    @Query("{ username: ?0 }")
    Optional<Auth> findByUsername(String username);
}
