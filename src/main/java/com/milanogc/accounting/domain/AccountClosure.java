package com.milanogc.accounting.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(
	name = "account_closure",
	uniqueConstraints = @UniqueConstraint(columnNames = {"ancestor_id", "descendant_id"})
)
@Entity
@IdClass(AccountClosureId.class)
@NamedQuery(
	name = "AccountClosure.findDescendants",
	query = "select ac.descendant from AccountClosure ac where ac.ancestor = ?1 and ac.descendant != ?1"
)
public class AccountClosure {
	@Id
	@ManyToOne
	@JoinColumn(name = "ancestor_id")
	Account ancestor;

	@Id
	@ManyToOne
	@JoinColumn(name = "descendant_id")
	Account descendant;

	public Account getAncestor() {
		return ancestor;
	}

	public void setAncestor(Account ancestor) {
		this.ancestor = ancestor;
	}

	public Account getDescendant() {
		return descendant;
	}

	public void setDescendant(Account descendant) {
		this.descendant = descendant;
	}
	
	public String toString() {
		return com.google.common.base.Objects.toStringHelper(this)
			.addValue(getAncestor())
			.addValue(getDescendant())
			.toString();
	}
}