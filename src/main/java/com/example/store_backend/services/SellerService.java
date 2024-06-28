package com.example.store_backend.services;

import com.example.store_backend.domain.Seller;
import com.example.store_backend.dto.SellerDto;
import com.example.store_backend.dto.UserSessionDto;
import com.example.store_backend.exception.SellerException;

import java.util.List;

public interface SellerService {

    public Seller addSeller(Seller seller);

    public List<Seller> getAllSellers() throws SellerException;

    public Seller getSellerById(Integer sellerId)throws SellerException;

    public Seller getSellerByMobile(String mobile, String token) throws SellerException;

    public Seller getCurrentlyLoggedInSeller(String token) throws SellerException;

    public UserSessionDto updateSellerPassword(SellerDto sellerDTO, String token) throws SellerException;

    public Seller updateSeller(Seller seller, String token)throws SellerException;

    public Seller updateSellerMobile(SellerDto sellerdto, String token)throws SellerException;

    public Seller deleteSellerById(Integer sellerId, String token)throws SellerException;



}
