package com.service.banking.service;

import java.util.List;

import com.service.banking.model.Customer;

public interface CustomerService {
	
	public Customer findById(Long custId);
	public Customer saveCustomer(Customer cust);
	public List<Customer> getCustomers();
}
