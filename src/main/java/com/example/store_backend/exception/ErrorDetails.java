package com.example.store_backend.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDetails {
    private LocalDateTime localDateTime;
    private  String message;
    private String details;


}
