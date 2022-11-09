package com.service.banking.service;

import java.util.List;

import com.service.banking.model.Offer;

public interface OfferService {
	
	public Offer save(Offer offer);
	public void deleteById(Long id);
	public Offer getById(Long id);
	public Offer getByName(String name);
	public List<Offer> getAll();
	
}
