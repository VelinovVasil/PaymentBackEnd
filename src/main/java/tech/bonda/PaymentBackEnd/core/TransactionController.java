package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.transaction.Transaction;
import tech.bonda.PaymentBackEnd.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    public String add(@RequestBody Transaction transaction) {
        transactionService.saveTransaction(transaction);
        return "New transaction is added";
    }

    @GetMapping("/getAll")
    public List<Transaction> getAll() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/get/{id}")
    public Transaction get(@PathVariable int id) {
        return transactionService.getAllTransactions().get(id);
    }
}
