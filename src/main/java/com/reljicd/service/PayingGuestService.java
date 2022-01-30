package com.reljicd.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reljicd.model.Pg;
import com.reljicd.model.Product;
import com.reljicd.model.User;

public interface PayingGuestService {
    Optional<Pg> findByPhoneNo(String phone);

    Optional<Pg> findByEmail(String email);
    Optional<Pg> findById(Long id);
    Pg saveUser(Pg user);
    Optional<Pg> findByUsername(String username);
    
    Page<Pg> findAllPgsPageable(Pageable pageable);
    
    void removePg(Pg pg);
}
