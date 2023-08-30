package tech.bonda.PaymentBackEnd.service;

import tech.bonda.PaymentBackEnd.entities.account.Account;

import java.util.List;

public interface AccountService {
    public Account saveAccount(Account account);

    public List<Account> getAllAccounts();
}
