package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.account.Account;
import tech.bonda.PaymentBackEnd.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
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

    @GetMapping("/getFirstThree")
    public List<Account> getFirstThree() {
        return accountService.getAllAccounts().subList(0, 3);
    }

    @GetMapping("/get/{id}")
    public Account get(@PathVariable int id) {
        return accountService.getAllAccounts().get(id);
    }


    @GetMapping("/getByEgn/{egn}")
    public Account getByEgn(@PathVariable String egn) {
        for (Account account : accountService.getAllAccounts())
        {
            if (account.getEgn().equals(egn))
            {
                return account;
            }
        }
        return null;
    }


    @GetMapping("/getByName/{name}")
    public Account getByName(@PathVariable String name) {
        for (Account account : accountService.getAllAccounts())
        {
            if (account.getName().equals(name))
            {
                return account;
            }
        }
        return null;
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        accountService.deleteAccount(id);
        return "Account with id " + id + " is deleted";
    }


}