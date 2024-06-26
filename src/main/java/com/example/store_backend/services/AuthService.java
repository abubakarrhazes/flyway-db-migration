package com.example.store_backend.services;

import com.example.store_backend.domain.UserSession;
import com.example.store_backend.dto.CustomerDto;
import com.example.store_backend.dto.SellerDto;
import com.example.store_backend.dto.UserSessionDto;

public interface AuthService {

    public UserSession loginCustomer(CustomerDto customerDto);

    public UserSessionDto logoutCustomer(UserSessionDto userSessionDto);

    public void checkTokenStatus(String token);

    public void deleteExpiredTokens();


    public UserSession loginSeller(SellerDto seller);

    public UserSessionDto logoutSeller(UserSessionDto userSessionDto);



}
