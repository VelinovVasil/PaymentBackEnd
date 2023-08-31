package tech.bonda.PaymentBackEnd.service;

import tech.bonda.PaymentBackEnd.entities.account.Account;

import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);

    List<Account> getAllAccounts();

    void deleteAccount(int id);


}
