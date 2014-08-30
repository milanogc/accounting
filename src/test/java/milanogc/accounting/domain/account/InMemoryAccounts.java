package milanogc.accounting.domain.account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class InMemoryAccounts implements Accounts {

  private Map<AccountId, Account> storage = new HashMap<>();

  @Override
  public AccountId createIdentity() {
    return new AccountId(UUID.randomUUID().toString().toUpperCase());
  }

  @Override
  public Account load(AccountId accountId) {
    return storage.get(accountId);
  }

  @Override
  public void store(Account account) {
    storage.put(account.accountId(), account);
  }
}
