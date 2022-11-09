package com.service.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.service.banking.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{

	
	@Query("select r from Role r where r.name = :name")
	public Role findByName(@Param("name") String name);
	
}
