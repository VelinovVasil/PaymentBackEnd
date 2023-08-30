package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.account.Account;
import tech.bonda.PaymentBackEnd.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public String add(@RequestBody Account account) {
        accountService.saveAccount(account);
        return "New account is added";
    }

    @GetMapping("/getAll")
    public List<Account> getAll() {
        return accountService.getAllAccounts();
    }
/*
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        accountService.deleteAccount(id);
        return "Account with id " + id + " is deleted";
    }
*/
}