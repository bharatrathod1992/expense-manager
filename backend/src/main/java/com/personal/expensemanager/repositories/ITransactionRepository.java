package com.personal.expensemanager.repositories;

import com.personal.expensemanager.entities.Account;
import com.personal.expensemanager.entities.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ITransactionRepository extends CrudRepository<Transaction,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Transaction t where t.debitFrom = ?1 OR t.creditTo = ?1")
    void deleteByAccountNumber(Account accno);
}
