package com.example.flyway_db_migration.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterDto {
    private String user_name;
    private String first_name;
    private  String last_name;
    private String other_name;
    private String email;
    private  String password;


}
