package com.milanogc.accounting.repository;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import com.milanogc.accounting.config.AppConfig;
import com.milanogc.accounting.domain.Account;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfig.class)
@Configurable
public class AccountRepositoryTest extends AbstractTestNGSpringContextTests {
	private static String ACCOUNT_NAME = "ASSET";
	
	@Inject
	private AccountRepository repository;
	
	private Account createAccount(String name) {
		Account account = new Account();
		account.setName(name);
		return account;
	}
	
	@Test
	public void createAccount() {
		Account account = createAccount(ACCOUNT_NAME);
		repository.save(account);
		Assert.notNull(account.getId());
	}
	
	@Test(dependsOnMethods = "createAccount", expectedExceptions = DataIntegrityViolationException.class)
	public void createAccountWithSameName() {
		createAccount();
	}
	
	@Test(dependsOnMethods = "createAccount")
	public void createParentAndChildAccount() {
		Account parent = createAccount("PARENT");
		Account child = createAccount("CHILD");
		child.setParent(parent);
		repository.save(parent);
		repository.save(child);
	}
	
	@Test(dependsOnMethods = "createParentAndChildAccount")
	public void countAccounts() {
		Assert.isTrue(repository.count() == 3L);
	}
}