package com.service.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.service.banking.model.Offer;

@Repository
public interface OfferRepo extends JpaRepository<Offer, Long> {

	@Query("select o from Offer o where o.offerName = :name")
	public Offer findByName(@Param("name") String name);
	
}
