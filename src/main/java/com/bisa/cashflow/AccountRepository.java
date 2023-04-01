package com.bisa.cashflow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

	List<AccountEntity> getByIdentificationDocumentAndStatus(Long identificationDocument, int value);

	List<AccountEntity> getByAccountCodeAndStatus(String accountCode, int value);

	List<AccountEntity> getByAccountCode(String accountCode);

}
