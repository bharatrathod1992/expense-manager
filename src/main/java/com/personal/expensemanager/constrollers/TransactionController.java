package com.personal.expensemanager.constrollers;

import com.personal.expensemanager.entities.Transaction;
import com.personal.expensemanager.services.TransactionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;


@CrossOrigin(origins = "*")
@RestController
public class TransactionController {
    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.GET, value = "/transactions")
    public Iterable<Transaction> getAllTransactions(){
        return this.transactionService.getAllTransactions();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/transaction/create")
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
