package com.example.store_backend.repositories;

import com.example.store_backend.domain.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository  extends JpaRepository<UserSession, Integer> {

    Optional<UserSession> findByToken(String token);

    Optional<UserSession> findByUserId(Integer userId);

}
