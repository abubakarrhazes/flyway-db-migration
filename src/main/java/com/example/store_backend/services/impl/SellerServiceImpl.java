package com.example.store_backend.services.impl;


import com.example.store_backend.domain.Seller;
import com.example.store_backend.domain.UserSession;
import com.example.store_backend.dto.SellerDto;
import com.example.store_backend.dto.UserSessionDto;
import com.example.store_backend.exception.AuthException;
import com.example.store_backend.exception.SellerException;
import com.example.store_backend.repositories.SellerRepository;
import com.example.store_backend.repositories.UserSessionRepository;
import com.example.store_backend.services.AuthService;
import com.example.store_backend.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl  implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public Seller addSeller(Seller seller) {
        Seller add= sellerRepository.save(seller);

        return add;
    }

    @Override
    public List<Seller> getAllSellers() throws SellerException {
        List<Seller> sellers= sellerRepository.findAll();

        if(sellers.size()>0) {
            return sellers;
        }
        else throw new SellerException("No Seller Found !");
    }

    @Override
    public Seller getSellerById(Integer sellerId) throws SellerException {
        Optional<Seller> seller = sellerRepository.findById(sellerId);

        if(seller.isPresent()) {
            return seller.get();
        }
        else throw new SellerException("Seller not found for this ID: "+sellerId);
    }

    @Override
    public Seller getSellerByMobile(String mobile, String token) throws SellerException {
        if(!token.contains("seller")) {
            throw new AuthException("Invalid session token for seller");
        }

        authService.checkTokenStatus(token);

        Seller existingSeller = sellerRepository.findByMobile(mobile).orElseThrow( () -> new SellerException("Seller not found with given mobile"));

        return existingSeller;
    }

    @Override
    public Seller getCurrentlyLoggedInSeller(String token) throws SellerException {
        if(!token.contains("seller")) {
            throw new AuthException("Invalid session token for seller");
        }

        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Seller existingSeller =sellerRepository.findById(user.getUserId()).orElseThrow(()->new SellerException("Seller not found for this ID"));

        return existingSeller;
    }

    @Override
    public UserSessionDto updateSellerPassword(SellerDto sellerDTO, String token) throws SellerException {
        if(!token.contains("seller")) {
            throw new AuthException("Invalid session token for seller");
        }


        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Optional<Seller> opt = sellerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new SellerException("Seller does not exist");

        Seller existingSeller = opt.get();


        if(!sellerDTO.getMobile().equals(existingSeller.getMobile())) {
            throw new SellerException("Verification error. Mobile number does not match");
        }

        existingSeller.setPassword(sellerDTO.getPassword());

        sellerRepository.save(existingSeller);

        UserSessionDto session = new UserSessionDto();

        session.setToken(token);

        authService.logoutSeller(session);

        session.setMessage("Updated password and logged out. Login again with new password");

        return session;
    }

    @Override
    public Seller updateSeller(Seller seller, String token) throws SellerException {
        if(!token.contains("seller")) {
            throw new AuthException("Invalid session token for seller");
        }

        authService.checkTokenStatus(token);

        Seller existingSeller=sellerRepository.findById(seller.getSellerId()).orElseThrow(()-> new SellerException("Seller not found for this Id: "+seller.getSellerId()));
        Seller newSeller= sellerRepository.save(seller);
        return newSeller;
    }

    @Override
    public Seller updateSellerMobile(SellerDto sellerdto, String token) throws SellerException {

        if(!token.contains("seller")) {
            throw new AuthException("Invalid session token for seller");
        }

        authService.checkTokenStatus(token);

        UserSession user = userSessionRepository.findByToken(token).get();

        Seller existingSeller=sellerRepository.findById(user.getUserId()).orElseThrow(()->new SellerException("Seller not found for this ID: "+ user.getUserId()));

        if(existingSeller.getPassword().equals(sellerdto.getPassword())) {
            existingSeller.setMobile(sellerdto.getMobile());
            return sellerRepository.save(existingSeller);
        }
        else {
            throw new SellerException("Error occured in updating mobile. Please enter correct password");
        }

    }

    @Override
    public Seller deleteSellerById(Integer sellerId, String token) throws SellerException {
        if(!token.contains("seller")) {
            throw new AuthException("Invalid session token for seller");
        }

        authService.checkTokenStatus(token);

        Optional<Seller> opt=sellerRepository.findById(sellerId);

        if(opt.isPresent()) {

            UserSession user = userSessionRepository.findByToken(token).get();

            Seller existingseller=opt.get();

            if(user.getUserId() == existingseller.getSellerId()) {
                sellerRepository.delete(existingseller);

                // logic to log out a seller after he deletes his account
                UserSessionDto session = new UserSessionDto();
                session.setToken(token);
                authService.logoutSeller(session);

                return existingseller;
            }
            else {
                throw new SellerException("Verification Error in deleting seller account");
            }
        }
        else throw new SellerException("Seller not found for this ID: "+sellerId);
    }
}
