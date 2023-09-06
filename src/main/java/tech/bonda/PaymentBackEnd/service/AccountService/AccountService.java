package tech.bonda.PaymentBackEnd.service.AccountService;

import com.fasterxml.jackson.databind.node.ObjectNode;
import tech.bonda.PaymentBackEnd.entities.account.Account;

import java.util.List;

public interface AccountService {
    ObjectNode saveAccount(Account account);

    List<Account> getAllAccounts();

    void deleteAccount(long id);

    Account getAccountById(long id);

    Account updateAccount(long id, Account account);

    ObjectNode loginAccount(Account account);

}
