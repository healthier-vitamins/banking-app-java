package com.service.banking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.banking.model.Customer;
import com.service.banking.model.Offer;
import com.service.banking.repo.CustomerRepo;
import com.service.banking.repo.OfferRepo;

@Service
public class OfferServiceImpl implements OfferService {

	@Autowired
	private OfferRepo offerRepo;
	
	@Override
	public Offer save(Offer offer) {
		return offerRepo.save(offer);
	}

	@Override
	public void deleteById(Long id) {
		offerRepo.deleteById(id);
	}

	@Override
	public Offer getById(Long id) {
		return offerRepo.getReferenceById(id);
	}

	@Override
	public Offer getByName(String name) {
		return offerRepo.findByName(name);
	}

	@Override
	public List<Offer> getAll() {
		return offerRepo.findAll();
	}
}