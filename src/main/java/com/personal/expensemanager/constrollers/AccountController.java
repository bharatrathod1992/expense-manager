package com.personal.expensemanager.constrollers;

import com.personal.expensemanager.entities.Account;
import com.personal.expensemanager.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public Account create(@RequestBody Account account) throws Exception {
        return this.accountService.create(account);
    }

    @RequestMapping(value = "/accounts/create/multiple", method = RequestMethod.POST)
    public Iterable<Account> createMultipleAccounts(@RequestBody List<Account> accountList){
        return this.accountService.createMultiple(accountList);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public Iterable<Account> findAllAccounts(){
        return this.accountService.findAll();
    }

    @RequestMapping(value = "/account/find/accno", method = RequestMethod.POST)
    public Account find(@RequestBody int accno) throws Exception {
        return this.accountService.findByAccNo(accno);
    }

    @RequestMapping(value = "/account/find/name", method = RequestMethod.POST)
    public Iterable<Account> findByName(@RequestBody String name) throws Exception {
        return this.accountService.findByName(name);
    }

    @RequestMapping(value = "/account/delete", method = RequestMethod.DELETE)
    public void delete(@RequestBody int accno) throws Exception {
        this.accountService.deleteByAccNo(accno);
    }

    @RequestMapping(value = "/account/update", method = RequestMethod.POST)
    public Account update(@RequestBody int accno, Account account) throws Exception {
        return this.accountService.updateByAccNo(accno, account);
    }
}
