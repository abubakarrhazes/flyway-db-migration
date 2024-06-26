package com.example.store_backend.repositories;

import com.example.store_backend.domain.Customer;
import com.example.store_backend.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findByMobile(String mobile);
    Optional<Seller> findByEmailId(String emailId);
    Optional<Seller> findByMobileNoOrEmailId(String mobileNo, String emailId);

}
