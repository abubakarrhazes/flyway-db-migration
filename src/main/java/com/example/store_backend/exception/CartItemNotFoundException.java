package com.example.store_backend.exception;

public class CartItemNotFoundException extends RuntimeException{

    public CartItemNotFoundException(){


    }

    public CartItemNotFoundException(String message){

        super(message);
    }
}
