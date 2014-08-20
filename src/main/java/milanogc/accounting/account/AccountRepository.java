package milanogc.accounting.account;

interface AccountRepository {
    AccountId nextIdentity();
    Account load(AccountId accountId);
    void store(Account account);
}