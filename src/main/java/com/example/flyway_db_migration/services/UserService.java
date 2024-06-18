package com.example.flyway_db_migration.services;


import com.example.flyway_db_migration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.flyway_db_migration.domain.User;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
}
