package com.example.store_backend.services.impl;

import com.example.store_backend.domain.Customer;
import com.example.store_backend.domain.Seller;
import com.example.store_backend.domain.UserSession;
import com.example.store_backend.dto.CustomerDto;
import com.example.store_backend.dto.SellerDto;
import com.example.store_backend.dto.UserSessionDto;
import com.example.store_backend.exception.AuthException;
import com.example.store_backend.exception.CustomerNotFoundException;
import com.example.store_backend.exception.SellerNotFoundException;
import com.example.store_backend.repositories.CustomerRepository;
import com.example.store_backend.repositories.SellerRepository;
import com.example.store_backend.repositories.UserSessionRepository;
import com.example.store_backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.List;
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
        String token = userSessionDto.getToken();

        checkTokenStatus(token);

        Optional<UserSession> opt = userSessionRepository.findByToken(token);

        if(opt.isEmpty())
            throw new AuthException("User not logged in. Invalid session token. Login Again.");

        UserSession session = opt.get();

        userSessionRepository.delete(session);

        userSessionDto.setMessage("Logged out successfully.");

        return userSessionDto;
    }

    @Override
    public void checkTokenStatus(String token) {
        Optional<UserSession> opt = userSessionRepository.findByToken(token);

        if(opt.isPresent()) {
            UserSession session = opt.get();
            LocalDateTime endTime = session.getSessionEndTime();
            boolean flag = false;
            if(endTime.isBefore(LocalDateTime.now())) {
                userSessionRepository.delete(session);
                flag = true;
            }

            deleteExpiredTokens();
            if(flag)
                throw new AuthException("Session expired. Login Again");
        }
        else {
            throw new AuthException("User not logged in. Invalid session token. Please login first.");
        }

    }

    @Override
    public void deleteExpiredTokens() {

        System.out.println("Inside delete tokens");

        List<UserSession> users = userSessionRepository.findAll();

        System.out.println(users);

        if(users.size() > 0) {
            for(UserSession user:users) {
                System.out.println(user.getUserId());
                LocalDateTime endTime = user.getSessionEndTime();
                if(endTime.isBefore(LocalDateTime.now())) {
                    System.out.println(user.getUserId());
                    userSessionRepository.delete(user);
                }
            }
        }
    }

    @Override
    public UserSession loginSeller(SellerDto seller) {

        Optional<Seller> res = sellerRepository.findByMobile(seller.getMobile());

        if(res.isEmpty())
            throw new SellerNotFoundException("Seller record does not exist with given mobile number");

        Seller existingSeller = res.get();

        Optional<UserSession> opt = userSessionRepository.findByUserId(existingSeller.getSellerId());

        if(opt.isPresent()) {

            UserSession user = opt.get();

            if(user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                userSessionRepository.delete(user);
            }
            else
                throw new AuthException("User already logged in");

        }


        if(existingSeller.getPassword().equals(seller.getPassword())) {

            UserSession newSession = new UserSession();

            newSession.setUserId(existingSeller.getSellerId());
            newSession.setUserType("seller");
            newSession.setSessionStartTime(LocalDateTime.now());
            newSession.setSessionEndTime(LocalDateTime.now().plusHours(1));

            UUID uuid = UUID.randomUUID();
            String token = "seller_" + uuid.toString().split("-")[0];

            newSession.setToken(token);

            return userSessionRepository.save(newSession);
        }
        else {
            throw new AuthException("Password Incorrect. Try again.");
        }
    }

    @Override
    public UserSessionDto logoutSeller(UserSessionDto userSessionDto) {

        String token = userSessionDto.getToken();

        checkTokenStatus(token);

        Optional<UserSession> opt = userSessionRepository.findByToken(token);

        if(opt.isEmpty())
            throw new AuthException("User not logged in. Invalid session token. Login Again.");

        UserSession user = opt.get();

        userSessionRepository.delete(user);

        userSessionDto.setMessage("Logged out successfully.");

        return userSessionDto;
    }
}
