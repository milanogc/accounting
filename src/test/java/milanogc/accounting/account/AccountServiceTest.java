package milanogc.accounting.account;

import javax.inject.Inject;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import com.milanogc.accounting.config.JpaRepositoryConfig;
import com.milanogc.accounting.config.ServiceConfig;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {JpaRepositoryConfig.class, ServiceConfig.class})
public class AccountServiceTest extends AbstractTestNGSpringContextTests {
	private static final String ACCOUNT_NAME = "ASSET";

	@Inject
	private AccountService accountService;

	private Account createAccount(String name) {
		Account account = new Account();
		account.setName(name);
		return account;
	}

	@Test
	public void createAccount() {
		Account account = createAccount(ACCOUNT_NAME);
		accountService.save(account);
		Assert.notNull(account.getId());
	}

	@Test(dependsOnMethods = "createAccount")
	public void createAccountChild() {
		Account account = createAccount("PIZZA");
		account.setParent(accountService.findOne(1L));
		accountService.save(account);
		Assert.notNull(account.getId());
	}

	@Test
	public void createTree() {
		Account milano = createAccount("Milano");
		accountService.save(milano);
		Account ativo = createAccount("Ativo");
		ativo.setParent(milano);
		accountService.save(ativo);
		Account circulante = createAccount("Circulante");
		circulante.setParent(ativo);
		accountService.save(circulante);
		Account aplicacoes = createAccount("Aplicações financeiras");
		aplicacoes.setParent(circulante);
		accountService.save(aplicacoes);
		Account fundos = createAccount("Fundos de investimento");
		fundos.setParent(aplicacoes);
		accountService.save(fundos);
		Account passivo = createAccount("Passivo");
		passivo.setParent(milano);
		accountService.save(passivo);
		System.out.println(accountService.findAncestors(fundos));
		accountService.print(milano);
	}
}
