package tech.bonda.PaymentBackEnd.service.AccountService;

import tech.bonda.PaymentBackEnd.entities.account.Account;

import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);

    List<Account> getAllAccounts();

    void deleteAccount(long id);

    Account getAccountById(long id);

    Account updateAccount(long id, Account account);

}
