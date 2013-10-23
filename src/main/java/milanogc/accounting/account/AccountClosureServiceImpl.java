package milanogc.accounting.account;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class AccountClosureServiceImpl implements AccountClosureService {
	@Inject AccountClosureRepository accountClosureRepository;

	@Override
	public void save(Account account) {
		accountClosureRepository.save(account);
	}

	@Override
	public List<Account> findDescendants(Account account) {
		return accountClosureRepository.findDescendants(account);
	}

	@Override
	public List<Account> findAncestors(Account account) {
		return accountClosureRepository.findAncestors(account);
	}
}
