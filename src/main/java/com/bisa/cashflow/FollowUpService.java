package com.bisa.cashflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowUpService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	MovementRepository movementRepository;

	public String addAccount(AccountDto accountDto) throws Exception {
		if (accountDto == null) {
			throw new Exception("No pudo registrarse la cuenta, los datos de la cuenta son invalidos.");
		}
		if (!accountDto.isNotEmpty())
			throw new Exception("No pudo registrarse la cuenta, algunos campos no estan correctos.");
		try {
			List<AccountEntity> lstAccountEntity = accountRepository.getByIdentificationDocumentAndStatus(
					CashFlowUtil.toLong(accountDto.getIdentificationDocument()), StatusEnum.ACTIVE.getValue());
			lstAccountEntity = lstAccountEntity == null ? new ArrayList<>() : lstAccountEntity;
			if (!lstAccountEntity.isEmpty()) {
				throw new Exception(
						"No pudo registrarse la cuenta, el numero de documento de indentidad esta duplicado.");
			}
			if (CashFlowUtil.toDouble(accountDto.getInitialBalance()) <= 0) {
				throw new Exception(
						"No pudo registrarse la cuenta, el monto de apertura no puede ser negativo o cero.");
			}
			String accountCode = CashFlowUtil.randomAccountCode() + accountDto.getIdentificationDocument()
					+ accountDto.getCurrency();
			AccountEntity accountEntity = new AccountEntity();
			accountEntity.setAccountCode(accountCode);
			accountEntity.setIdentificationDocument(CashFlowUtil.toLong(accountDto.getIdentificationDocument()));
			accountEntity.setFirstName(accountDto.getFirstName());
			accountEntity.setLastName(accountDto.getLastName());
			accountEntity.setAddress(accountDto.getAddress());
			accountEntity.setCurrency(CashFlowUtil.toInteger(accountDto.getCurrency()));
			accountEntity.setTotalAmount(CashFlowUtil.toDouble(accountDto.getInitialBalance()));
			accountRepository.save(accountEntity);

			MovementEntity movementEntity = new MovementEntity();
			movementEntity.setAccountCode(accountCode);
			movementEntity.setAmount(CashFlowUtil.toDouble(accountDto.getInitialBalance()));
			movementEntity.setStatus(StatusEnum.ACTIVE.getValue());
			movementEntity.setCurrency(CashFlowUtil.toInteger(accountDto.getCurrency()));
			movementRepository.save(movementEntity);
			return accountCode;
		} catch (Exception e) {
			throw new Exception("No pudo registrarse la cuenta." + e.getMessage());
		}
	}

	private void setDeposit(MovementEntity movementEntity, Double deposit, Double totalAmount, int statusAccount)
			throws Exception {
		deposit = deposit == null ? 0D : deposit;
		if (deposit < 0) {
			throw new Exception("No pudo registrarse el movimiento. No es necesario que adicione el signo negativo");
		}
		if (deposit > 0) {
			totalAmount = totalAmount == null ? 0D : totalAmount;
			if ((deposit + totalAmount) >= 0 && StatusEnum.HOLD.getValue() == statusAccount) {
				statusAccount = StatusEnum.ACTIVE.getValue();
			}
			totalAmount = totalAmount + deposit;
			movementEntity.setAmount(deposit);
		}
	}

	private void setWithdrawal(MovementEntity movementEntity, Double withdrawal, Double totalAmount, int statusAccount)
			throws Exception {
		withdrawal = withdrawal == null ? 0D : withdrawal;
		if (withdrawal < 0) {
			throw new Exception("No pudo registrarse el movimiento. No es necesario que adicione el signo negativo");
		}

		if (withdrawal > 0 && StatusEnum.ACTIVE.getValue() == statusAccount) {
			totalAmount = totalAmount == null ? 0D : totalAmount;
			movementEntity.setAmount(withdrawal * (-1D));
			totalAmount = totalAmount + withdrawal;
			if (totalAmount > withdrawal) {
				statusAccount = StatusEnum.HOLD.getValue();
			}
		}
	}

	public String moveMoney(String accountCode, Double deposit, Double withdrawal, Integer currency) throws Exception {
		if (!CashFlowUtil.isNotEmpty(accountCode)) {
			throw new Exception("No pudo registrarse el movimiento, los datos de la cuenta son invalidos.");
		}
		if (!CashFlowUtil.isNotEmpty(currency)) {
			throw new Exception("No pudo registrarse el movimiento, sin moneda.");
		}
		try {
			List<AccountEntity> lstAccountEntity = accountRepository.getByAccountCode(accountCode);
			lstAccountEntity = lstAccountEntity == null ? new ArrayList<>() : lstAccountEntity;
			if (lstAccountEntity.size() != 1) {
				throw new Exception("No pudo registrarse la cuenta, el numero de cuenta no existe.");
			}
			AccountEntity accountEntity = lstAccountEntity.get(0);
			if (!accountEntity.getCurrency().equals(currency)) {
				throw new Exception("No pudo registrarse el movimiento, sin moneda.");
			}

			Double totalAmount = movementRepository.addAmountActive(accountCode, StatusEnum.ACTIVE.getValue());
			MovementEntity movementEntity = new MovementEntity();
			int status = accountEntity.getStatus();
			setWithdrawal(movementEntity, withdrawal, totalAmount, status);
			setDeposit(movementEntity, deposit, totalAmount, status);
			accountEntity.setTotalAmount(totalAmount);
			accountEntity.setStatus(status);
			accountRepository.save(accountEntity);

			movementEntity.setAccountCode(accountCode);
			movementEntity.setCurrency(currency);
			movementEntity.setStatus(StatusEnum.ACTIVE.getValue());
			movementRepository.save(movementEntity);
			return "ok";
		} catch (Exception e) {
			throw new Exception("No pudo registrarse el movimiento." + e.getMessage());
		}
	}

	public List<String> checkBalance(String accountCode) throws Exception {
		if (!CashFlowUtil.isNotEmpty(accountCode)) {
			throw new Exception("No pudo registrarse el movimiento, los datos de la cuenta son invalidos.");
		}
		List<String> lstMovements = new ArrayList<>();
		try {
			List<MovementEntity> lstMovementEntity = movementRepository.getByAccountCodeAndStatus(accountCode,
					StatusEnum.ACTIVE.getValue());
			lstMovementEntity = lstMovementEntity == null ? new ArrayList<>() : lstMovementEntity;
			if (lstMovementEntity.isEmpty()) {
				throw new Exception("No pudo listarse los movimientos. (resultado vacio)");
			}
			int hlp = 1;
			String txt = "", m = "";
			for (MovementEntity x : lstMovementEntity) {
				txt = "";
				txt = txt + "MOV(" + hlp + "):";
				switch (x.getCurrency()) {
				case 1:
					m = "BOLIVIANOS";
					break;
				case 2:
					m = "DOLARES";
					break;
				default:
					m = "";
					break;
				}
				if (x.getAmount() >= 0)
					txt += x.getAmount() + "(DEPOSITO " + m + ")";
				else
					txt += (x.getAmount() * (-1D)) + "(RETIRO " + m + ")";
				lstMovements.add(txt);
				hlp++;
			}

			return lstMovements;
		} catch (Exception e) {
			throw new Exception("No pudo listarse los movimientos." + e.getMessage());
		}
	}

}
