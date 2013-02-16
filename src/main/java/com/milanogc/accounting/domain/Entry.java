package com.milanogc.accounting.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnore;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"posting_id", "account_id"}))
@Entity
public class Entry {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "posting_id")
	@JsonIgnore
	private Posting posting;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@Column(nullable=false)
	private Integer amount;

	public Entry() {
	}

	public Entry(Posting posting, Account account, int amount) {
		setPosting(posting);
		setAccount(account);
		setAmount(amount);
	}

	public Posting getPosting() {
		return posting;
	}

	public void setPosting(Posting posting) {
		this.posting = posting;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
