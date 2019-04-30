package com.personal.expensemanager.constrollers;

import com.personal.expensemanager.entities.Account;
import com.personal.expensemanager.services.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
@Api(value = "accounts", description = "Operations related to accounts like bank accounts, wallet money and cash")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "Create account by providing required information", response = Account.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public Account create(@RequestBody Account account) throws Exception {
        return this.accountService.create(account);
    }

    @ApiOperation(value = "Create multiple accounts at a time by providing required information", response = Iterable.class)
    @RequestMapping(value = "/create/multiple", method = RequestMethod.POST, produces = "application/json")
    public Iterable<Account> createMultipleAccounts(@RequestBody List<Account> accountList){
        return this.accountService.createMultiple(accountList);
    }

    @ApiOperation(value = "Find a list of accounts", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/accounts", method = RequestMethod.POST, produces = "application/json")
    public Iterable<Account> findAllAccounts(){
        return this.accountService.findAll();
    }

    @ApiOperation(value = "Find account by account number", response = Account.class)
    @RequestMapping(value = "/find/accno", method = RequestMethod.POST, produces = "application/json")
    public Account findByAccNo(@RequestBody int accno) throws Exception {
        return this.accountService.findByAccNo(accno);
    }

    @ApiOperation(value = "Find accounts by account name", response = Iterable.class)
    @RequestMapping(value = "/find/name", method = RequestMethod.POST, produces = "application/json")
    public Iterable<Account> findByName(@RequestBody String name) throws Exception {
        return this.accountService.findByName(name);
    }

    @ApiOperation(value = "Delete account by providing account number")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestBody int accno) throws Exception {
        this.accountService.deleteByAccNo(accno);
    }

    @ApiOperation(value = "Update the information of particular account", response = Account.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public Account update(@RequestBody int accno, Account account) throws Exception {
        return this.accountService.updateByAccNo(accno, account);
    }
}
