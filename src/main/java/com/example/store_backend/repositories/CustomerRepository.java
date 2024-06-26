package com.example.store_backend.repositories;

import com.example.store_backend.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByMobileNo(String mobileNo);

    Optional<Customer> findByEmailId(String emailId);

    Optional<Customer> findByMobileNoOrEmailId(String mobileNo, String emailId);

}
