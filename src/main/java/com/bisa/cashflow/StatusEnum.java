package com.bisa.cashflow;

public enum StatusEnum {
	REMOVED(2), ACTIVE(1), HOLD(3);

	private int value;

	StatusEnum(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return CashFlowUtil.isNotEmpty(this.value) ? String.valueOf(this.value) : "";
	}

	public int getValue() {
		return this.value;
	}

	public boolean compare(Integer estado) {
		if (CashFlowUtil.isNotEmpty(estado)) {
			return this.value == estado;
		}
		return false;
	}
}
