package com.personal.expensemanager.repositories;

import com.personal.expensemanager.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface IAccountRepository extends CrudRepository<Account,Integer> {
    Iterable<Account> findByName(String name);
}
