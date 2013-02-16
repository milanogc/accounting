package com.milanogc.accounting.repository;

import org.springframework.data.repository.CrudRepository;

import com.milanogc.accounting.domain.Posting;

public interface PostingRepository extends CrudRepository<Posting, Long> {
}