package com.bisa.cashflow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
	private String identificationDocument;
	private String firstName;
	private String lastName;
	private String address;
	private String initialBalance;
	private String currency;

	public boolean isNotEmpty() {
		return CashFlowUtil.isNotEmpty(identificationDocument) && CashFlowUtil.isNotEmpty(lastName)
				&& CashFlowUtil.isNotEmpty(initialBalance) && CashFlowUtil.isNotEmpty(currency);
	}

}
