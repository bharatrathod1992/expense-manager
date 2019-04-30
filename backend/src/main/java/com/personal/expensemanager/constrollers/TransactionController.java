package com.personal.expensemanager.constrollers;

import com.personal.expensemanager.entities.Transaction;
import com.personal.expensemanager.services.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transaction")
@Api(value = "transactions", description = "Operations pertaining to transactions in Expense manager")
public class TransactionController {
    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "Find list of transactions which are made till date", response = Iterable.class)
    @RequestMapping(method = RequestMethod.GET, value = "/transactions", produces = "application/json")
    public Iterable<Transaction> getAllTransactions(){
        return this.transactionService.getAllTransactions();
    }

    @ApiOperation(value = "Create transaction by providing required information", response = Transaction.class)
    @RequestMapping(method = RequestMethod.POST, value = "/create", produces = "application/json")
    public Transaction createNewTransaction(@RequestBody Transaction transaction){
        Transaction result = new Transaction();
        try {
            result = this.transactionService.create(transaction);
        } catch (Exception e) {
            logger.debug("Exception : ",e);
        }
        return result;
    }
}
