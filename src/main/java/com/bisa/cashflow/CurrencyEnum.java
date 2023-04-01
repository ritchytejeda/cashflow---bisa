package com.bisa.cashflow;

public enum CurrencyEnum {
	BOLIVIANOS(1), DOLARES(2), YENS(3);

	private int value;

	CurrencyEnum(int value) {
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

	CurrencyEnum getById(Integer currency) {
		switch (currency) {
		case 1: valueOf(null);
			this.name();
			break;

		default:
			break;
		}
		return null;
	}
}
