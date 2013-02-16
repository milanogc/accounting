package com.milanogc.accounting.domain;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Posting {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date date = new Date();

	@OneToMany(fetch = FetchType.EAGER)
	private List<Entry> entries = Collections.emptyList();

	public Posting() {
	}

	public Posting debit(Account account, int amount) {
		return credit(account, -amount);
	}

	public Posting credit(Account account, int amount) {
		Entry entry = new Entry(this, account, amount);
		entries.add(entry);
		return this;
	}

	public void commit() {
		if (!isBalanced()) {
		}
	}

	public boolean isBalanced() {
		int sum = 0;

		for (Entry e : entries) {
			sum += e.getAmount();
		}

		return sum == 0;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
}
