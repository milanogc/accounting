package milanogc.accounting.account;

import java.util.List;

interface AccountClosureService {
	void save(Account account);
	List<Account> findDescendants(Account account);
	List<Account> findAncestors(Account account);
}
