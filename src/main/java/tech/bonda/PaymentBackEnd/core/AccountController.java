package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.account.Account;
import tech.bonda.PaymentBackEnd.service.AccountService.AccountService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController implements Controller {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @PostMapping("/register")
    public String create(@RequestBody Object object) {
        return accountService.saveAccount((Account) object);
    }

    @Override
    @GetMapping("/getAll")
    public List<Object> getAll() {
        return Collections.singletonList(Collections.singleton(accountService.getAllAccounts()));
    }

    @Override
    @GetMapping("/get/{id}")
    public Account get(@PathVariable long id) {
        return accountService.getAccountById(id);
    }

    @Override
    @PostMapping("/update/{id}")
    public Account update(@PathVariable long id, @RequestBody Object object) {
        return accountService.updateAccount(id, (Account) object);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        accountService.deleteAccount(id);
    }
}
