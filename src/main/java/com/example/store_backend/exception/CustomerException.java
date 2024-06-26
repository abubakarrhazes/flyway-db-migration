package com.example.store_backend.exception;

public class CustomerException extends RuntimeException{
    public CustomerException(){

    }
    public CustomerException(String message){
        super(message);


    }
}
