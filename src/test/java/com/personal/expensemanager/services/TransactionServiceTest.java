package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Account;
import com.personal.expensemanager.entities.Transaction;
import com.personal.expensemanager.enums.AccountType;
import com.personal.expensemanager.enums.TransactionType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Sql(value = {"/sql/seed.sql"})
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubCategoryService subCategoryService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldAddNewRecordForTransactionOfTypeIncomeIfAccountNumberPresent() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.INCOME);
        transaction.setCreditTo(this.accountService.findByAccNo(5555));
        transaction.setAmount(50000);

        Transaction result = this.transactionService.create(transaction);
        assertEquals(4,result.getId());
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotAddNewRecordForTransactionOfTypeIncomeIfAccountNotPresent() throws Exception {
        Account account = new Account(9999,"SBI",AccountType.BANK,10000);
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.INCOME);
        transaction.setCreditTo(account);
        transaction.setAmount(500);

        Transaction result = this.transactionService.create(transaction);
    }
    @Test
    public void shouldAddMoneyToTheAccounForINCOME() throws Exception {
        Account account = this.accountService.findByAccNo(5555);
        assertEquals(5555,account.getAccno());
        assertEquals(200,account.getAmount(),0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.INCOME);
        transaction.setCreditTo(account);
        transaction.setAmount(500);

        Transaction result = this.transactionService.create(transaction);
        assertEquals(4,result.getId());

        assertEquals(700,this.accountService.findByAccNo(5555).getAmount(),0);
    }
    @Test
    public void shouldAddNewRecordForTransactionOfTypeDebitIfAccountNumberPresent() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.EXPENSE);
        transaction.setDebitFrom(this.accountService.findByAccNo(5555));
        transaction.setAmount(50);
        transaction.setCategory(this.categoryService.findById(1));
        transaction.setSubCategory(this.subCategoryService.findById(1));

        Transaction result = this.transactionService.create(transaction);
        assertEquals(4,result.getId());
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotAddNewRecordForTransactionOfTypeDebitIfAccountNumberNotPresent() throws Exception {
        Account acc = new Account();
        acc.setAccno(7777);
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.EXPENSE);
        transaction.setDebitFrom(acc);
        transaction.setAmount(50);
        transaction.setCategory(this.categoryService.findById(1));
        transaction.setSubCategory(this.subCategoryService.findById(1));

        Transaction result = this.transactionService.create(transaction);
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotAddNewRecordForTransactionOfTypeDebitIfNotEnoughBalanceInAccountNumber() throws Exception {
        Account acc = this.accountService.findByAccNo(5555);
        assertEquals(200,acc.getAmount(),0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.EXPENSE);
        transaction.setDebitFrom(acc);
        transaction.setAmount(500);
        transaction.setCategory(this.categoryService.findById(1));
        transaction.setSubCategory(this.subCategoryService.findById(1));

        Transaction result = this.transactionService.create(transaction);
    }
    @Test
    public void shouldDebitAmountFromRespectedAccount() throws Exception {
        Account acc = this.accountService.findByAccNo(5555);
        assertEquals(200,acc.getAmount(),0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.EXPENSE);
        transaction.setDebitFrom(acc);
        transaction.setAmount(100);
        transaction.setCategory(this.categoryService.findById(1));
        transaction.setSubCategory(this.subCategoryService.findById(1));

        Transaction result = this.transactionService.create(transaction);
        assertEquals(4,result.getId());
        assertEquals(5555,result.getDebitFrom().getAccno());
        assertEquals(100,this.accountService.findByAccNo(5555).getAmount(),0);
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotAddNewTransactionInCaseOfTransferIfDebitingAccountNotFound() throws Exception {
        Account debitAcc = new Account();
        debitAcc.setAccno(1234);
        Account creditAcc = this.accountService.findByAccNo(1267);
        assertEquals(555.5,creditAcc.getAmount(),0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setDebitFrom(debitAcc);
        transaction.setCreditTo(creditAcc);
        transaction.setAmount(1000);

        Transaction result = this.transactionService.create(transaction);
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotAddNewTransactionInCaseOfTransferIfCreditingAccountNotFound() throws Exception {
        Account debitAcc = this.accountService.findByAccNo(1267);
        assertEquals(555.5,debitAcc.getAmount(),0);
        Account creditAcc = new Account();
        creditAcc.setAccno(1234);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setDebitFrom(debitAcc);
        transaction.setCreditTo(creditAcc);
        transaction.setAmount(1000);

        Transaction result = this.transactionService.create(transaction);
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotAddNewTransactionInCaseOfTransferIfDebitingAccountDoesNotHaveEnoughBalance() throws Exception {
        Account debitAcc = this.accountService.findByAccNo(5555);
        assertEquals(200,debitAcc.getAmount(),0);
        Account creditAcc = this.accountService.findByAccNo(1267);
        assertEquals(555.5,creditAcc.getAmount(),0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setDebitFrom(debitAcc);
        transaction.setCreditTo(creditAcc);
        transaction.setAmount(1000);

        Transaction result = this.transactionService.create(transaction);
    }
    @Test
    public void shouldAddNewTransactionInCaseOfTransferIfAllGood() throws Exception {
        Account debitAcc = this.accountService.findByAccNo(1267);
        assertEquals(555.5,debitAcc.getAmount(),0);
        Account creditAcc = this.accountService.findByAccNo(5555);
        assertEquals(200,creditAcc.getAmount(),0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setDebitFrom(debitAcc);
        transaction.setCreditTo(creditAcc);
        transaction.setAmount(150);

        Transaction result = this.transactionService.create(transaction);
        assertEquals(4,result.getId());
    }
    @Test
    public void shouldDebitAndCreditAmountToTheRespectedAccountNumbersInCaseOfTransferIfAllGood() throws Exception {
        Account debitAcc = this.accountService.findByAccNo(1267);
        assertEquals(555.5,debitAcc.getAmount(),0);
        Account creditAcc = this.accountService.findByAccNo(5555);
        assertEquals(200,creditAcc.getAmount(),0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setDebitFrom(debitAcc);
        transaction.setCreditTo(creditAcc);
        transaction.setAmount(300);

        Transaction result = this.transactionService.create(transaction);
        assertEquals(4,result.getId());

        assertEquals(500,this.accountService.findByAccNo(5555).getAmount(),0);
        assertEquals(255.5,this.accountService.findByAccNo(1267).getAmount(),0);
    }
}