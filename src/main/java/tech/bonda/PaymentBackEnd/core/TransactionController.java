package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.transaction.Transaction;
import tech.bonda.PaymentBackEnd.service.TransactionService.TransactionService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/transaction")
public class TransactionController{
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/register")
    public Transaction create(@RequestBody Object object) {
        return transactionService.saveTransaction((Transaction) object);
    }

    @GetMapping("/getAll")
    public List<Transaction> getAll() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/get/{id}")
    public Transaction get(@PathVariable long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping("/update/{id}")
    public Transaction update(@PathVariable long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        transactionService.deleteTransaction(id);
    }
}
