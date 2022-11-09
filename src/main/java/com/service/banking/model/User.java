package com.service.banking.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Column(unique = true)
	private String username;
	private String password;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "cust_id")
	private Customer customer;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Collection<Role> roles = new ArrayList<>();

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String name, String username, String password, Customer customer, Collection<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.customer = customer;
		this.roles = roles;
	}
	
//	public User(String name, String username, String password, BankAccount bankAcc, Collection<Role> roles) {
//		super();
//		this.name = name;
//		this.username = username;
//		this.password = password;
//		this.bankAcc = bankAcc;
//		this.roles = roles;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", customer="
				+ customer + ", roles=" + roles + "]";
	}
	
}
