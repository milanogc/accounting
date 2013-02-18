package com.milanogc.accounting.domain;

import java.io.Serializable;

public class AccountClosureId implements Serializable {
	private static final long serialVersionUID = -4189626972936165203L;

	private Account ancestor;
	private Account descendant;

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
}