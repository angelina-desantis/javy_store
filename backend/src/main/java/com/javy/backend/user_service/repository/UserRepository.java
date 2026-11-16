package com.javy.backend.user_service.repository;

import com.javy.backend.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by their email address.
     *
     * @param email the user's email address
     * @return an Optional containing the user if found, or empty otherwise
     */
    Optional<User> findByEmail(String email);

}
