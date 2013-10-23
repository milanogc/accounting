package milanogc.accounting.account;

import javax.inject.Inject;

import milanogc.accounting.account.Account;
import milanogc.accounting.account.AccountRepository;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import com.milanogc.accounting.config.JpaRepositoryConfig;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = JpaRepositoryConfig.class)
@Configurable
public class AccountRepositoryTest extends AbstractTestNGSpringContextTests {
	private static String ACCOUNT_NAME = "ASSET";

	@Inject
	private AccountRepository repository;

	@Inject
	private PlatformTransactionManager transactionManager;

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
	public void differentObjectsWithoutTransaction() {
		Account account = repository.findOne(1L);
		account.setName("TEST");
		Account sameAccount = repository.findOne(1L);
		Assert.isTrue(!account.getName().equals(sameAccount.getName()));
	}

	@Test(dependsOnMethods = "createAccount", expectedExceptions = IllegalArgumentException.class)
	public void sameObjectsWithtTransaction() {
		new TransactionTemplate(transactionManager).execute(new TransactionCallback<Object>() {
			public Object doInTransaction(TransactionStatus status) {
				differentObjectsWithoutTransaction();
				return null;
			}
		});
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
