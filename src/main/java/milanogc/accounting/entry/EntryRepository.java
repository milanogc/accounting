package milanogc.accounting.entry;

import org.springframework.data.repository.CrudRepository;

interface EntryRepository extends CrudRepository<Entry, Long> {
}