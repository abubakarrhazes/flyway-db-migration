package com.example.flyway_db_migration.controllers;


import com.example.flyway_db_migration.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class UserController {


    @Autowired
    private UserService userService;



}
