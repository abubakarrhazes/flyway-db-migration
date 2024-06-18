package com.example.flyway_db_migration.repository;

import com.example.flyway_db_migration.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {




}
