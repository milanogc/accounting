package milanogc.accounting.domain.account;

interface PostingRepository {

  PostingId nextIdentity();

  Posting load(PostingId postingId);

  void store(Posting posting);
}
