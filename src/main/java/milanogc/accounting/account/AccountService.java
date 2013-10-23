package milanogc.accounting.account;

import java.util.List;

public interface AccountService {
	Account save(Account account);
	Account findOne(Long id);
	List<Account> findDescendants(Account account);
	List<Account> findAncestors(Account account);
	void print(Account account);
}