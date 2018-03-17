package com.personal.expensemanager.repositories;

import com.personal.expensemanager.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAccountRepository extends CrudRepository<Account,Integer> {

    String findAccountsByLists = "select * from accounts where accno = 123";

    Iterable<Account> findByName(String name);

    @Query(value = findAccountsByLists, nativeQuery = true)
    List<Account> findAccounts(List<Integer> accounts);
}
