package com.service.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.banking.model.BankAccount;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {

}
