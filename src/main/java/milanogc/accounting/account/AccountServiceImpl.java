package milanogc.accounting.account;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
class AccountServiceImpl implements AccountService {
	@Inject
	private AccountRepository accountRepository;

	@Inject
	private AccountClosureService accountClosureService;

	@Override
	@Transactional
	public Account save(Account account) {
		account = accountRepository.save(account);
		accountClosureService.save(account);
		return account;
	}

	@Override
	@Transactional(readOnly = true)
	public Account findOne(Long id) {
		return accountRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Account> findDescendants(Account account) {
		return accountClosureService.findDescendants(account);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Account> findAncestors(Account account) {
		return accountClosureService.findAncestors(account);
	}

	@Override
	public void print(Account account) {
		print(account, findDescendants(account), "", true);
	}

	private void print(Account account, List<Account> descendants, String prefix, boolean isTail) {
		System.out.println(prefix + (isTail ? "└── " : "├── ") + account.getName());

		for (Iterator<Account> iterator = filterDescendants(account, descendants).iterator(); iterator.hasNext(); ) {
			print(iterator.next(), descendants, prefix + (isTail ? "	" : "│   "), !iterator.hasNext());
		}
	}

	private static List<Account> filterDescendants(Account account, List<Account> descendants) {
		List<Account> filtered = Lists.newArrayList();

		for (Account descendant : descendants) {
			if (descendant.getParent().equals(account)) {
				filtered.add(descendant);
			}
		}

		descendants.removeAll(filtered);
		return filtered;
	}
}
