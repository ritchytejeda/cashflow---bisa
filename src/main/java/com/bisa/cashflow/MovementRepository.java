package com.bisa.cashflow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, Integer> {

	@Query(value = " select sum(amount) from MOVEMENT where account_Code= :code "
			+ "		   and status = :status ", nativeQuery = true)
	Double addAmountActive(@Param("code") String accountCode, @Param("status") int status);

	List<MovementEntity> getByAccountCodeAndStatus(String accountCode, int value);

}
