package milanogc.accounting.domain.account;

interface Postings {

  PostingId nextIdentity();

  Posting load(PostingId postingId);

  void store(Posting posting);
}
