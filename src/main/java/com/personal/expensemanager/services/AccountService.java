package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Account;
import org.assertj.core.util.Lists;
import com.personal.expensemanager.repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(Account accno) throws Exception {
        Optional<Account> check = Optional.ofNullable(this.accountRepository.findOne(accno.getAccno()));
        if(check.isPresent()){
            throw new Exception("Account number already exist");
        }
        return this.accountRepository.save(accno);
    }

    public Account findByAccNo(int accno) throws Exception {
        Optional<Account> check = Optional.ofNullable(this.accountRepository.findOne(accno));
        if(check.isPresent()){
            return this.accountRepository.findOne(accno);
        }else{
            throw new Exception("Account number not found");
        }
    }

    public Iterable<Account> findAll() {
        return this.accountRepository.findAll();
    }

    public Iterable<Account> findByName(String name) throws Exception {
        return this.accountRepository.findByName(name);
    }

    public Account updateByAccNo(int accno, Account account) throws Exception {
        Optional<Account> oAccount = Optional.ofNullable(this.accountRepository.findOne(accno));
        if(oAccount.isPresent()){
            Account prev = this.accountRepository.findOne(accno);
            prev.setName(account.getName());
            prev.setAccountType(account.getAccountType());
            prev.setAmount(account.getAmount());
            return this.accountRepository.save(prev);
        }else{
            throw new Exception("No record found with this account number");
        }
    }

    public void deleteByAccNo(int accno) throws Exception {
        Optional<Account> oAccount = Optional.ofNullable(this.accountRepository.findOne(accno));
        if(oAccount.isPresent()){
            this.accountRepository.delete(accno);
        }else{
            throw new Exception("No record found with this account number");
        }
    }
}
