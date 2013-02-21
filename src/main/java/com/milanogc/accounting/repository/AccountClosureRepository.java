package com.milanogc.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milanogc.accounting.domain.Account;
import com.milanogc.accounting.domain.AccountClosure;
import com.milanogc.accounting.domain.AccountClosureId;

public interface AccountClosureRepository extends JpaRepository<AccountClosure, AccountClosureId>, AccountClosureRepositoryCustom {
	List<Account> findDescendants(Account account);
	List<Account> findAncestors(Account account);
}
