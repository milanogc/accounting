package milanogc.accounting.domain.account;

// repository
interface Postings {

  PostingId nextIdentity();

  Posting load(PostingId postingId);

  void store(Posting posting);
}
