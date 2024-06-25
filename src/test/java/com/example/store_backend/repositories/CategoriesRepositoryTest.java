package com.example.store_backend.repositories;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoriesRepositoryTest {

    @Autowired
    private CategoriesRepository categoriesRepository;


    //Arrange Act Assert

    @Test
    public void CategoriesRepository_SaveAll_CreateNewCategory(){

        //Arrange


        //Act

        //Assert




    }


}
