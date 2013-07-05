package com.milanogc.accounting.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milanogc.accounting.domain.Account;
import com.milanogc.accounting.repository.AccountClosureRepository;
import com.milanogc.accounting.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
	@Inject
	private AccountRepository accountRepository;

	@Inject
	private AccountClosureRepository accountClosureRepository;

	@Transactional
	public Account save(Account account) {
		account = accountRepository.save(account);
		accountClosureRepository.save(account);
		return account;
	}

	@Transactional(readOnly = true)
	public Account findOne(Long id) {
		return accountRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Account> findDescendants(Account account) {
		return accountClosureRepository.findDescendants(account);
	}

	@Transactional(readOnly = true)
	public List<Account> findAncestors(Account account) {
		return accountClosureRepository.findAncestors(account);
	}
}
