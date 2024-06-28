package com.example.store_backend.services.impl;

import com.example.store_backend.domain.*;
import com.example.store_backend.dto.CustomerDto;
import com.example.store_backend.dto.CustomerUpdateDto;
import com.example.store_backend.dto.UserSessionDto;
import com.example.store_backend.exception.AuthException;
import com.example.store_backend.exception.CustomerException;
import com.example.store_backend.exception.CustomerNotFoundException;
import com.example.store_backend.repositories.CustomerRepository;
import com.example.store_backend.repositories.UserSessionRepository;
import com.example.store_backend.services.AuthService;
import com.example.store_backend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserSessionRepository userSessionRepository;


    @Override
    public Customer addCustomer(Customer customer) {
        customer.setCreatedOn(LocalDateTime.now());

        Cart c = new Cart();

        System.out.println(c);

//		System.out.println(c.getProducts().size());

        customer.setCustomerCart(c);


        customer.setOrders(new ArrayList<Order>());


        Optional<Customer> existing = customerRepository.findByMobileNo(customer.getMobileNo());

        if(existing.isPresent())
            throw new CustomerException("Customer already exists. Please try to login with your mobile no");

        customerRepository.save(customer);

        return customer;
    }


    @Override
    public Customer getLoggedInCustomerDetails(String token){

        if(!token.contains("customer")) {
            throw new AuthException("Invalid session token for customer");
        }

        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        return existingCustomer;
    }


    @Override
    public List<Customer> getAllCustomers(String token) throws CustomerNotFoundException {

        // update to seller

        if(!token.contains("seller")) {
            throw new AuthException("Invalid session token.");
        }

        authService.checkTokenStatus(token);

        List<Customer> customers = customerRepository.findAll();

        if(customers.size() == 0)
            throw new CustomerNotFoundException("No record exists");

        return customers;
    }

    @Override
    public Customer updateCustomer(CustomerUpdateDto customer, String token) throws CustomerNotFoundException {


        if(!token.contains("customer")) {
            throw new AuthException("Invalid session token for customer");
        }

        authService.checkTokenStatus(token);

        Optional<Customer> opt = customerRepository.findByMobileNo(customer.getMobileNo());

        Optional<Customer> res = customerRepository.findByEmailId(customer.getEmailId());

        if(opt.isEmpty() && res.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist with given mobile no or email-id");

        Customer existingCustomer = null;

        existingCustomer = opt.orElseGet(res::get);

        UserSession user = userSessionRepository.findByToken(token).get();

        if(Objects.equals(existingCustomer.getCustomerId(), user.getUserId())) {

            if(customer.getFirstName() != null) {
                existingCustomer.setFirstName(customer.getFirstName());
            }

            if(customer.getLastName() != null) {
                existingCustomer.setLastName(customer.getLastName());
            }

            if(customer.getEmailId() != null) {
                existingCustomer.setEmailId(customer.getEmailId());
            }

            if(customer.getMobileNo() != null) {
                existingCustomer.setMobileNo(customer.getMobileNo());
            }

            if(customer.getPassword() != null) {
                existingCustomer.setPassword(customer.getPassword());
            }

            if(customer.getAddress() != null) {
                for(Map.Entry<String, Address> values : customer.getAddress().entrySet()) {
                    existingCustomer.getAddress().put(values.getKey(), values.getValue());
                }
            }

            customerRepository.save(existingCustomer);
            return existingCustomer;

        }
        else {
            throw new CustomerException("Error in updating. Verification failed.");
        }


    }

    // Method to update customer mobile number - details updated for current logged in user

    @Override
    public Customer updateCustomerMobileNoOrEmailId(CustomerUpdateDto customerUpdateDto, String token) throws CustomerNotFoundException {

        if(!token.contains("customer")) {
            throw new AuthException("Invalid session token for customer");
        }

        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        if(customerUpdateDto.getEmailId() != null) {
            existingCustomer.setEmailId(customerUpdateDto.getEmailId());
        }


        existingCustomer.setMobileNo(customerUpdateDto.getMobileNo());

        customerRepository.save(existingCustomer);

        return existingCustomer;

    }

    @Override
    public Customer updateCreditCardDetails(String token, CreditCard card) throws CustomerException {
        if(!token.contains("customer")) {
            throw new AuthException("Invalid session token for customer");
        }

        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        existingCustomer.setCreditCard(card);

        return customerRepository.save(existingCustomer);
    }

    @Override
    public UserSessionDto updateCustomerPassword(CustomerDto customerDto, String token) throws CustomerNotFoundException {

        if(!token.contains("customer")) {
            throw new AuthException("Invalid session token for customer");
        }


        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();


        if(!customerDto.getMobileId().equals(existingCustomer.getMobileNo())) {
            throw new CustomerException("Verification error. Mobile number does not match");
        }

        existingCustomer.setPassword(customerDto.getPassword());

        customerRepository.save(existingCustomer);

        UserSessionDto session = new UserSessionDto();

        session.setToken(token);

        authService.logoutCustomer(session);

        session.setMessage("Updated password and logged out. Login again with new password");

        return session;
    }

    @Override
    public UserSessionDto deleteCustomer(CustomerDto customerDto, String token) throws CustomerNotFoundException {
        if(!token.contains("customer")) {
            throw new AuthException("Invalid session token for customer");
        }

        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        UserSessionDto session = new UserSessionDto();

        session.setMessage("");

        session.setToken(token);

        if(existingCustomer.getMobileNo().equals(customerDto.getMobileId())
                && existingCustomer.getPassword().equals(customerDto.getPassword())) {

            customerRepository.delete(existingCustomer);

            authService.logoutCustomer(session);

            session.setMessage("Deleted account and logged out successfully");

            return session;
        }
        else {
            throw new CustomerException("Verification error in deleting account. Please re-check details");
        }
    }

    @Override
    public Customer updateAddress(Address address, String type, String token) throws CustomerException {
        if(!token.contains("customer")) {
            throw new AuthException("Invalid session token for customer");
        }

        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        existingCustomer.getAddress().put(type, address);

        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer deleteAddress(String type, String token) throws CustomerException, CustomerNotFoundException {
        if(!token.contains("customer")) {
            throw new AuthException("Invalid session token for customer");
        }

        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        if(!existingCustomer.getAddress().containsKey(type))
            throw new CustomerException("Address type does not exist");

        existingCustomer.getAddress().remove(type);

        return customerRepository.save(existingCustomer);
    }

    @Override
    public List<Order> getCustomerOrders(String token) throws CustomerException {
        if(!token.contains("customer")) {
            throw new AuthException("Invalid session token for customer");
        }

        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        List<Order> myOrders = existingCustomer.getOrders();

        if(myOrders.size() == 0)
            throw new CustomerException("No orders found");

        return myOrders;
    }
}
