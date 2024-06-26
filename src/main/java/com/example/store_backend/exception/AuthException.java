package com.example.store_backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthException extends  RuntimeException{
  public  AuthException(String message){
      super(message);

  }

}
