package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Account;
import com.personal.expensemanager.repositories.IAccountRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private static final String ACC_NO_NOT_FOUND="Account number not found";

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    public void setAccountRepository(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(Account accno) {
        Optional<Account> check = Optional.ofNullable(this.accountRepository.findOne(accno.getAccno()));
        if(check.isPresent()){
            throw new IllegalArgumentException("Account number already exist");
        }
        return this.accountRepository.save(accno);
    }

    public Account findByAccNo(int accno) throws NotFoundException {
        Optional<Account> check = Optional.ofNullable(this.accountRepository.findOne(accno));
        if(check.isPresent()){
            return this.accountRepository.findOne(accno);
        }else{
            throw new NotFoundException(ACC_NO_NOT_FOUND);
        }
    }

    public Iterable<Account> findAll() {
        return this.accountRepository.findAll();
    }

    public Iterable<Account> findByName(String name) {
        return this.accountRepository.findByName(name);
    }

    public Account updateByAccNo(int accno, Account account) throws NotFoundException {
        Optional<Account> oAccount = Optional.ofNullable(this.accountRepository.findOne(accno));
        if(oAccount.isPresent()){
            Account prev = this.accountRepository.findOne(accno);
            prev.setName(account.getName());
            prev.setAccountType(account.getAccountType());
            prev.setAmount(account.getAmount());
            return this.accountRepository.save(prev);
        }else{
            throw new NotFoundException(ACC_NO_NOT_FOUND);
        }
    }

    public void deleteByAccNo(int accno) throws NotFoundException {
        Optional<Account> oAccount = Optional.ofNullable(this.accountRepository.findOne(accno));
        if(oAccount.isPresent()){
            Account account = this.accountRepository.findOne(accno);
            this.transactionService.deleteRelatedTransactions(account);
            this.accountRepository.delete(accno);
        }else{
            throw new NotFoundException(ACC_NO_NOT_FOUND);
        }
    }

    public Iterable<Account> createMultiple(List<Account> accountList) {
        List<Integer> accounts;
        accounts = accountList.stream().map(account -> {
            return account.getAccno();
        }).collect(Collectors.toList());

        List<Account> accountsResult = this.accountRepository.findAccounts(accounts);

        if(!accountsResult.isEmpty()){
            throw new IllegalArgumentException("Account number(s) already exists");
        }

        return this.accountRepository.save(accountList);
    }
}
