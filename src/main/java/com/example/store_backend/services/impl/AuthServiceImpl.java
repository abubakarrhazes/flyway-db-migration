package com.example.store_backend.services.impl;

import com.example.store_backend.domain.Customer;
import com.example.store_backend.domain.UserSession;
import com.example.store_backend.dto.CustomerDto;
import com.example.store_backend.dto.SellerDto;
import com.example.store_backend.dto.UserSessionDto;
import com.example.store_backend.exception.AuthException;
import com.example.store_backend.exception.CustomerNotFoundException;
import com.example.store_backend.repositories.CustomerRepository;
import com.example.store_backend.repositories.SellerRepository;
import com.example.store_backend.repositories.UserSessionRepository;
import com.example.store_backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;


    @Override
    public UserSession loginCustomer(CustomerDto customerDto) {
        Optional<Customer> res = customerRepository.findByMobileNo(customerDto.getMobileId());

        if(res.isEmpty())
            throw new CustomerNotFoundException("Customer record does not exist with given mobile number");

        Customer existingCustomer = res.get();

        Optional<UserSession> opt = userSessionRepository.findByUserId(existingCustomer.getCustomerId());

        if(opt.isPresent()) {

            UserSession user = opt.get();

            if(user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                userSessionRepository.delete(user);
            }
            else
                throw new AuthException("User already logged in");

        }


        if(existingCustomer.getPassword().equals(customerDto.getPassword())) {

            UserSession newSession = new UserSession();

            newSession.setUserId(existingCustomer.getCustomerId());
            newSession.setUserType("customer");
            newSession.setSessionStartTime(LocalDateTime.now());
            newSession.setSessionEndTime(LocalDateTime.now().plusHours(1));

            UUID uuid = UUID.randomUUID();
            String token = "customer_" + uuid.toString().split("-")[0];

            newSession.setToken(token);

            return userSessionRepository.save(newSession);
        }
        else {
            throw new AuthException("Password Incorrect. Try again.");
        }
    }

    @Override
    public UserSessionDto logoutCustomer(UserSessionDto userSessionDto) {
        return null;
    }

    @Override
    public void checkTokenStatus(String token) {

    }

    @Override
    public void deleteExpiredTokens() {

    }

    @Override
    public UserSession loginSeller(SellerDto seller) {
        return null;
    }

    @Override
    public UserSessionDto logoutSeller(UserSessionDto userSessionDto) {
        return null;
    }
}
