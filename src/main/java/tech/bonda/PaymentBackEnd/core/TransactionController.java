package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.transaction.Transaction;
import tech.bonda.PaymentBackEnd.service.TransactionService.TransactionService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController implements Controller {
    @Autowired
    private TransactionService transactionService;

    @Override
    @PostMapping("/create")
    public Transaction create(@RequestBody Object object) {
        return transactionService.saveTransaction((Transaction) object);
    }

    @Override
    @GetMapping("/getAll")
    public List<Object> getAll() {
        return Collections.singletonList(transactionService.getAllTransactions());
    }

    @Override
    @GetMapping("/get/{id}")
    public Transaction get(@PathVariable long id) {
        return transactionService.getTransactionById(id);
    }

    @Override
    @PostMapping("/update/{id}")
    public Transaction update(@PathVariable long id, @RequestBody Object object) {
        return transactionService.updateTransaction(id, (Transaction) object);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        transactionService.deleteTransaction(id);
    }
}
