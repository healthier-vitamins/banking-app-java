package com.service.banking.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.service.banking.model.Role;
import com.service.banking.model.User;
import com.service.banking.repo.RoleRepo;
import com.service.banking.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user == null) throw new UsernameNotFoundException("[TERMINAL] -- " + username + " not found --");
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public User saveUser(User user) {
		System.out.println("[TERMINAL] -- saving user " + user.getUsername() + " --");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
	public User saveUpdatedUser(User user) {
		System.out.println("[TERMINAL] -- saving updated user " + user.getUsername() + " --");
		return userRepo.save(user);
	}
	
	@Override
	public User getUser(String username) {
		System.out.println("[TERMINAL] -- fetching " + username + " --");
		return userRepo.findByUsername(username);
	}

	@Override
	public void deleteById(Long id) {
		userRepo.deleteById(id);
	}
	
	@Override
	public List<User> getUsers() {
		System.out.println("[TERMINAL] -- fetching all users --");
		return userRepo.findAll();
	}
	
	@Override
	public User getUserById(Long id) {
		return userRepo.getReferenceById(id);
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	// for init
	@Override
	public Role saveRole(Role role) {
		System.out.println("[TERMINAL] -- saving role " + role.getName() + " --");
		return roleRepo.save(role);
	}
	
	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName);
		user.getRoles().add(role);
		userRepo.save(user);
		System.out.println("[TERMINAL] -- added " + role + " to user " + username + " --");
	}

}
