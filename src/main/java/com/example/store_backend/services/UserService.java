package com.example.store_backend.services;


import com.example.store_backend.domain.User;
import com.example.store_backend.dto.RegisterDto;
import com.example.store_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    // Get All Users In the Database

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User addUser(User user){
        return userRepository.save(user);
    }


}
