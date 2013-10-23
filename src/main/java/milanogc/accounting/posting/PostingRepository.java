package milanogc.accounting.posting;

import org.springframework.data.repository.CrudRepository;

interface PostingRepository extends CrudRepository<Posting, Long> {
}