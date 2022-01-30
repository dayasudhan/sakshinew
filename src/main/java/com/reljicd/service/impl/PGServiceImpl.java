package com.reljicd.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.reljicd.model.Pg;
import com.reljicd.repository.PgRepository;
import com.reljicd.repository.ProductRepository;
import com.reljicd.service.PayingGuestService;
import com.reljicd.service.ProductService;

@Service
public class PGServiceImpl implements PayingGuestService{
	PgRepository pgRepository;
	
	   @Autowired
	    public PGServiceImpl(PgRepository pgRepository) {
	        this.pgRepository = pgRepository;
	    }
	@Override
	public Optional<Pg> findByPhoneNo(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Pg> findByEmail(String email) {
		// TODO Auto-generated method stub
		return pgRepository.findByEmail(email);
	}

	@Override
	public Optional<Pg> findById(Long id) {
		// TODO Auto-generated method stub
		return pgRepository.findById(id);
	}

	@Override
	public Pg saveUser(Pg user) {
		// TODO Auto-generated method stub
		return pgRepository.saveAndFlush(user);
	}
	@Override
	public Optional<Pg> findByUsername(String username) {
		// TODO Auto-generated method stub
		return pgRepository.findByUsername(username);
	}
	@Override
	public Page<Pg> findAllPgsPageable(Pageable pageable) {
		// TODO Auto-generated method stub
		return pgRepository.findAll(pageable);
	}
	@Override
	public void removePg(Pg pg) {
		pgRepository.delete(pg.getId());
		
	}

}
