package com.example.store_backend.exception;

public class SellerNotFoundException extends RuntimeException{

    public SellerNotFoundException() {
        super();
    }


    public SellerNotFoundException(String message) {
        super(message);
    }
}