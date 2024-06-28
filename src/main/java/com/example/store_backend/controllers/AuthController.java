package com.example.store_backend.controllers;


import com.example.store_backend.domain.Customer;
import com.example.store_backend.domain.Seller;
import com.example.store_backend.domain.UserSession;
import com.example.store_backend.dto.CustomerDto;
import com.example.store_backend.dto.SellerDto;
import com.example.store_backend.dto.UserSessionDto;
import com.example.store_backend.services.AuthService;
import com.example.store_backend.services.CustomerService;
import com.example.store_backend.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//Request Mapping Url For Authentication
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthService authService;

    @Autowired
    private SellerService sellerService;



    @PostMapping("/register/customer")
    public ResponseEntity<Customer> registerAccountHandler(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
    }

    // Handler to login a user
    @PostMapping(value = "/login/customer", consumes = "application/json")
    public ResponseEntity<UserSession> loginCustomerHandler(@Valid @RequestBody CustomerDto customerdto){
        return new ResponseEntity<>(authService.loginCustomer(customerdto), HttpStatus.ACCEPTED);
    }

    // Handler to logout a user

    @PostMapping(value = "/logout/customer", consumes = "application/json")
    public ResponseEntity<UserSessionDto> logoutCustomerHandler(@RequestBody UserSessionDto sessionToken){
        return new ResponseEntity<>(authService.logoutCustomer(sessionToken), HttpStatus.ACCEPTED);
    }

    /*********** SELLER REGISTER LOGIN LOGOUT HANDLER ************/

    @PostMapping(value = "/register/seller", consumes = "application/json")
    public ResponseEntity<Seller> registerSellerAccountHandler(@Valid @RequestBody Seller seller) {
        return new ResponseEntity<>(sellerService.addSeller(seller), HttpStatus.CREATED);
    }


    // Handler to login a user

    @PostMapping(value = "/login/seller", consumes = "application/json")
    public ResponseEntity<UserSession> loginSellerHandler(@Valid @RequestBody SellerDto seller){
        return new ResponseEntity<>(authService.loginSeller(seller), HttpStatus.ACCEPTED);
    }


    // Handler to logout a user

    @PostMapping(value = "/logout/seller", consumes = "application/json")
    public ResponseEntity<UserSessionDto> logoutSellerHandler(@RequestBody UserSessionDto sessionToken){
        return new ResponseEntity<>(authService.logoutSeller(sessionToken), HttpStatus.ACCEPTED);
    }




}
