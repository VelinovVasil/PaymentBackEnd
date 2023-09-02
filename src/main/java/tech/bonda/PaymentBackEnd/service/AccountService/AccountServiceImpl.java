package tech.bonda.PaymentBackEnd.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bonda.PaymentBackEnd.entities.account.Account;
import tech.bonda.PaymentBackEnd.repository.AccountRepository;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }
    
    @Override
    public Account getAccountById(long id) {
        return accountRepository.findById(id).orElse(null);
    }
    
    @Override
    public Account updateAccount(long id, Account account) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        existingAccount.setName(account.getName());
        existingAccount.setEgn(account.getEgn());
        existingAccount.setPhoneNumber(account.getPhoneNumber());
        existingAccount.setDateOfCreation(account.getDateOfCreation());
        return accountRepository.save(existingAccount);
    }
}
