package com.service.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.service.banking.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
//	@Query("select u from User u where u.username = :username")
//	public User findByUsername(@Param("username") String username);
	
	public User findByUsername(String username);
	
}
	
