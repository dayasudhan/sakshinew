package com.reljicd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.reljicd.model.Pg;
import com.reljicd.model.Product;
import com.reljicd.model.User;

public interface PgRepository  extends JpaRepository<Pg, Long>{
	Optional<Pg> findById(Long id);
	
    Optional<Pg> findByEmail(@Param("email") String email);

    Optional<Pg> findByUsername(@Param("username") String username);

}
