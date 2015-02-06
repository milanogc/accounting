package milanogc.accounting.domain.account;

// repository
interface Accounts {

  AccountId createIdentity();

  Account load(AccountId accountId);

  void store(Account account);
}