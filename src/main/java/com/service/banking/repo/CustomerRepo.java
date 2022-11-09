package com.service.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.service.banking.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

	@Query("select c from Customer c where c.custFirstName = :firstName")
	public Customer findByFirstName(@Param("firstName") String firstName);
	
}
