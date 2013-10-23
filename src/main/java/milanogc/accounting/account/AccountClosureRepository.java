package milanogc.accounting.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface AccountClosureRepository extends JpaRepository<AccountClosure, AccountClosureId>, AccountClosureRepositoryCustom {
	List<Account> findDescendants(Account account);
	List<Account> findAncestors(Account account);
}
