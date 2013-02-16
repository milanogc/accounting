package com.milanogc.accounting.repository;

import org.springframework.data.repository.CrudRepository;

import com.milanogc.accounting.domain.Entry;

public interface EntryRepository extends CrudRepository<Entry, Long> {
}