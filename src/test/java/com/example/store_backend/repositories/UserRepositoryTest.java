package com.example.store_backend.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {



    @Autowired
    private UserRepository userRepository;


    //ARRANGE ACT ASSERT

    @Test
    public void UserRepository_SaveAll_CreateUser(){

        //Arrange


        //Act

        //Assert




    }

}