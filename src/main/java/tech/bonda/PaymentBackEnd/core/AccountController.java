package tech.bonda.PaymentBackEnd.core;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.account.Account;
import tech.bonda.PaymentBackEnd.service.AccountService.AccountService;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/account")
public class AccountController{

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ObjectNode create(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @GetMapping("/getAll")
    public List<Account> getAll() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/get/{id}")
    public Account get(@PathVariable long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/update/{id}")
    public Account update(@PathVariable long id, @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        accountService.deleteAccount(id);
    }

    @PostMapping("/login")
    public ObjectNode login(@RequestBody Account account) {
        return accountService.loginAccount(account);
    }
}
