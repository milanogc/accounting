package milanogc.accounting.domain.account;

interface AccountRepository {

  AccountId createIdentity();

  Account load(AccountId accountId);

  void store(Account account);
}