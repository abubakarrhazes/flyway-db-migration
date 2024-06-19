package com.example.store_backend.controllers;


import com.example.store_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class UserController {
    @Autowired
    private UserService userService;




}
