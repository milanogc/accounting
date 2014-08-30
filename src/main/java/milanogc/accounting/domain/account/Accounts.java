package milanogc.accounting.domain.account;

interface Accounts {

  AccountId createIdentity();

  Account load(AccountId accountId);

  void store(Account account);
}