package com.finaxis.finaxis.repository;

import com.finaxis.finaxis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameAndPhoneNumber(String username, String phoneNumber);

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
