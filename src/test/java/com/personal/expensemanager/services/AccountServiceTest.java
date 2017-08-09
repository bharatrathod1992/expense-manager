package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Account;
import com.personal.expensemanager.enums.AccountType;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/sql/seed.sql"})
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    //CREATE
    @Test
    public void shouldCreateNewAccount() throws Exception {
        Account account = new Account(1234,"HDFC", AccountType.BANK,500.00);
        Account first = this.accountService.create(account);
        assertEquals(1234,first.getAccno());
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotCreateDuplicateAccount() throws Exception {
        Account account1 = new Account(1234,"HDFC", AccountType.BANK,500.00);
        Account first = this.accountService.create(account1);
        assertEquals(1234,first.getAccno());
        Account account2 = new Account(1234,"HDFC", AccountType.BANK,500.00);
        Account second = this.accountService.create(account2);
    }

    //READ
    @Test
    public void shouldFindAccountById() throws Exception {
        Account account = new Account(5678,"HDFC", AccountType.BANK,100.00);
        Account account1 = this.accountService.create(account);
        assertEquals(5678,account1.getAccno());

        Account result = this.accountService.findByAccNo(account.getAccno());
        assertEquals(5678,result.getAccno());
        assertEquals("HDFC",result.getName());
        assertEquals(100,result.getAmount(),0);
        assertEquals(AccountType.BANK,result.getAccountType());
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotFindAccountIfWrongId() throws Exception {
        Account account = new Account(5678,"HDFC", AccountType.BANK,100.00);
        Account account1 = this.accountService.create(account);
        assertEquals(5678,account1.getAccno());

        Account result = this.accountService.findByAccNo(1232);
    }
    @Test
    public void shouldFindAccountByName() throws Exception {
        Account account = new Account(5678,"HDFC", AccountType.BANK,1000.00);
        Account account1 = this.accountService.create(account);
        assertEquals(5678,account1.getAccno());

        List<Account> result = Lists.newArrayList(this.accountService.findByName("HDFC"));
        assertEquals("HDFC",result.get(0).getName());
        assertEquals(5678,result.get(0).getAccno());
        assertEquals(1000,result.get(0).getAmount(),0);
        assertEquals(AccountType.BANK,result.get(0).getAccountType());
    }
    @Test
    public void shouldNotFindAccountByNameIfWrongName() throws Exception {
        Account account = new Account(5678,"HDFC", AccountType.BANK,1000.00);
        Account account1 = this.accountService.create(account);
        assertEquals(5678,account1.getAccno());

        List<Account> result = Lists.newArrayList(this.accountService.findByName("HDF"));
        assertEquals(0,result.size());
    }
    @Test
    public void shouldFindAllAccounts() throws Exception {
            Account account1 = new Account(5678, "HDFC", AccountType.BANK, 10000.00);
            Account first = this.accountService.create(account1);
            assertEquals(5678, first.getAccno());
            Account account2 = new Account(5679, "HDFC", AccountType.BANK, 1000.00);
            Account second = this.accountService.create(account2);
            assertEquals(5679, second.getAccno());

            List<Account> result = Lists.newArrayList(this.accountService.findAll());
            assertEquals(4,result.size());
            assertEquals("HDFC",result.get(2).getName());
            assertEquals(5678,result.get(2).getAccno());
            assertEquals(1000,result.get(3).getAmount(),0);
            assertEquals(AccountType.BANK,result.get(0).getAccountType());
    }

    //UPDATE
    @Test
    public void shouldUpdateAccountDetailsByAccNo() throws Exception {
        Account account = new Account();
        account.setAccno(4444);
        account.setName("Union");
        account.setAccountType(AccountType.BANK);
        account.setAmount(2000);
        Account created = this.accountService.create(account);
        assertEquals(4444,created.getAccno());
        assertEquals("Union",created.getName());
        assertEquals(account.getAccountType(),created.getAccountType());
        assertEquals(2000,created.getAmount(),0);

        Account toBeUpdate = new Account();
        toBeUpdate.setAmount(3000);
        toBeUpdate.setAccountType(AccountType.WALLET);
        toBeUpdate.setName("PayTM");
        Account updated = this.accountService.updateByAccNo(account.getAccno(),toBeUpdate);
        assertEquals(4444,updated.getAccno());
        assertEquals("PayTM",updated.getName());
        assertEquals(updated.getAccountType(),updated.getAccountType());
        assertEquals(3000,updated.getAmount(),0);
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotUpdateAccountDetailsByAccNoIfNotFound() throws Exception {
        Account account = new Account();
        account.setAccno(3333);
        account.setName("Union");
        account.setAccountType(AccountType.BANK);
        account.setAmount(2000);
        Account created = this.accountService.create(account);
        assertEquals(3333,created.getAccno());
        assertEquals("Union",created.getName());
        assertEquals(account.getAccountType(),created.getAccountType());
        assertEquals(2000,created.getAmount(),0);

        Account toBeUpdate = new Account();
        toBeUpdate.setAmount(3000);
        toBeUpdate.setAccountType(AccountType.WALLET);
        toBeUpdate.setName("PayTM");
        Account updated = this.accountService.updateByAccNo(5412,toBeUpdate);
    }

    //DELETE
    @Test
    public void shouldDeleteAccountByAccno() throws Exception {
        List<Account> accounts = Lists.newArrayList(this.accountService.findAll());
        assertEquals(2,accounts.size());
        this.accountService.deleteByAccNo(1267);
        List<Account> accounts1 = Lists.newArrayList(this.accountService.findAll());
        assertEquals(1,accounts1.size());
    }
    @Test(expected = java.lang.Exception.class)
    public void shouldNotDeleteAccountByAccnoIfNotFound() throws Exception {
        List<Account> accounts = Lists.newArrayList(this.accountService.findAll());
        assertEquals(2,accounts.size());
        this.accountService.deleteByAccNo(1272);
        List<Account> accounts1 = Lists.newArrayList(this.accountService.findAll());
        assertEquals(0,accounts1.size());
    }
}