package com.example.store_backend.services;

import com.example.store_backend.domain.Address;
import com.example.store_backend.domain.CreditCard;
import com.example.store_backend.domain.Customer;
import com.example.store_backend.domain.Order;
import com.example.store_backend.dto.CustomerDto;
import com.example.store_backend.dto.CustomerUpdateDto;
import com.example.store_backend.dto.UserSessionDto;
import com.example.store_backend.exception.CustomerException;
import com.example.store_backend.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    public Customer addCustomer(Customer customer)
            throws CustomerException;

    public Customer getLoggedInCustomerDetails(String token)
            throws CustomerNotFoundException;

    public List<Customer> getAllCustomers(String token)
            throws CustomerNotFoundException;

    public Customer updateCustomer(CustomerUpdateDto customerUpdateDto, String token)
            throws CustomerNotFoundException;

    public Customer updateCustomerMobileNoOrEmailId(CustomerUpdateDto customerUpdateDto, String token)
            throws CustomerNotFoundException;

    public Customer updateCreditCardDetails(String token, CreditCard card)
            throws CustomerException;

    public UserSessionDto updateCustomerPassword(CustomerDto customerDto, String token)
            throws CustomerNotFoundException;

    public UserSessionDto deleteCustomer(CustomerDto customerDto, String token)
            throws CustomerNotFoundException;

    public Customer updateAddress(Address address, String type, String token)
            throws CustomerException;

    public Customer deleteAddress(String type, String token)
            throws CustomerException, CustomerNotFoundException;

    public List<Order> getCustomerOrders(String token)
            throws CustomerException;
}
