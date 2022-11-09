package com.service.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.banking.model.Customer;
import com.service.banking.service.CustomerService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {
	
	@Autowired
	private CustomerService custService;
	
	@GetMapping("/customer/get/{id}")
	public ResponseEntity<Customer> getById(@PathVariable Long id) {
		return ResponseEntity.ok().body(custService.findById(id));
	}
	
	@PostMapping("/customer/save")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer cust) {
		return ResponseEntity.ok().body(custService.saveCustomer(cust));
	}
	
	@GetMapping("/customer/all")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok().body(custService.getCustomers());
	}
	
}
