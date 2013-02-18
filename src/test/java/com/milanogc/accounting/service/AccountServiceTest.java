package com.milanogc.accounting.service;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import com.milanogc.accounting.config.AppConfig;
import com.milanogc.accounting.domain.Account;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfig.class)
@Configurable
public class AccountServiceTest extends AbstractTestNGSpringContextTests {
	private static final String ACCOUNT_NAME = "ASSET";

	@Inject
	private AccountService service;

	private Account createAccount(String name) {
		Account account = new Account();
		account.setName(name);
		return account;
	}

	@Test
	public void createAccount() {
		Account account = createAccount(ACCOUNT_NAME);
		service.save(account);
		Assert.notNull(account.getId());
	}

	@Test(dependsOnMethods = "createAccount")
	public void createAccountChild() {
		Account account = createAccount("PIZZA");
		account.setParent(service.findOne(1L));
		service.save(account);
		Assert.notNull(account.getId());
	}
}
