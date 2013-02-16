package com.milanogc.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milanogc.accounting.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}