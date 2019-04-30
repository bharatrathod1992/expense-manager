package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Account;
import com.personal.expensemanager.entities.Transaction;
import com.personal.expensemanager.repositories.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;

@Service
public class TransactionService {
    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public void setTransactionRepository(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction create(Transaction transaction) throws Exception {
        switch (transaction.getTransactionType()) {
            case INCOME:
                Account account = null;
                account = this.accountService.findByAccNo(transaction.getCreditTo().getAccno());
                account.setAmount(account.getAmount() + transaction.getAmount());
                this.accountService.updateByAccNo(account.getAccno(), account);
                return this.transactionRepository.save(transaction);
            case EXPENSE:
                Account debitAcc = null;
                debitAcc = this.accountService.findByAccNo(transaction.getDebitFrom().getAccno());
                if(debitAcc.getAmount() - transaction.getAmount() < 0){
                    throw new InsufficientResourcesException("You don't have enough balance in this account");
                }else{
                    debitAcc.setAmount(debitAcc.getAmount() - transaction.getAmount());
                }
                this.accountService.updateByAccNo(debitAcc.getAccno(),debitAcc);
                return this.transactionRepository.save(transaction);
            case TRANSFER:
                Account debitAccNo = null;
                Account creditAccNo = null;
                debitAccNo = this.accountService.findByAccNo(transaction.getDebitFrom().getAccno());
                creditAccNo = this.accountService.findByAccNo(transaction.getCreditTo().getAccno());
                if(debitAccNo.getAmount() - transaction.getAmount() < 0){
                    throw new InsufficientResourcesException("You don't have enough balance in this account to transfer");
                }else{
                    debitAccNo.setAmount(debitAccNo.getAmount() - transaction.getAmount());
                    creditAccNo.setAmount(creditAccNo.getAmount() + transaction.getAmount());
                }
                this.accountService.updateByAccNo(debitAccNo.getAccno(),debitAccNo);
                this.accountService.updateByAccNo(creditAccNo.getAccno(),creditAccNo);
                return this.transactionRepository.save(transaction);
        }
        return null;
    }

    public void deleteRelatedTransactions(Account account) {
        this.transactionRepository.deleteByAccountNumber(account);
    }

    public Iterable<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }
}
