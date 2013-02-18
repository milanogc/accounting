package com.milanogc.accounting.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.milanogc.accounting.domain.Account;
import com.milanogc.accounting.repository.AccountClosureRepositoryCustom;

@Repository
public class AccountClosureRepositoryImpl implements AccountClosureRepositoryCustom {
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	private static final String INSERT_SELF_CLOSURE = "insert into account_closure (ancestor_id, descendant_id) values (:account, :account)";
	private static final String INSERT_OTHERS_CLOSURES = "insert into account_closure (ancestor_id, descendant_id) select ancestor_id, :descendant from account_closure where descendant_id = :ancestor";
	// it would be a single insert but h2 do not support adding the following: union all select :descendant, :descendant

	public void save(Account account) {
		Query query = entityManager.createNativeQuery(INSERT_SELF_CLOSURE);
		query.setParameter("account", account.getId());
		query.executeUpdate();

		if (account.getParent() != null) {
			query = entityManager.createNativeQuery(INSERT_OTHERS_CLOSURES);
			query.setParameter("ancestor", account.getParent().getId());
			query.setParameter("descendant", account.getId());
			query.executeUpdate();
		}
	}
}
