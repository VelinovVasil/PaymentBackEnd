package tech.bonda.PaymentBackEnd.service.AccountService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bonda.PaymentBackEnd.config.ErrorHandling.EGNexeption;
import tech.bonda.PaymentBackEnd.config.ErrorHandling.LoginFailedException;
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
    public ObjectNode saveAccount(Account account) {
        Account existingAccount = accountRepository.findByEgn(account.getEgn());
        if (existingAccount != null)
        {
            throw new EGNexeption("EGN already exists: " + account.getEgn());
        }

        // Hashing the password
        String plainTextPassword = account.getPassword();
        String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        account.setPassword(hashedPassword);

        accountRepository.save(account);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("id", account.getId());
        jsonNode.put("name", account.getName());

        return jsonNode;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public boolean deleteAccount(long id) {
        if (accountRepository.existsById(id))
        {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Account getAccountById(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account updateAccount(long id, Account account) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount != null)
        {
            existingAccount.setName(account.getName());
            existingAccount.setEgn(account.getEgn());
            existingAccount.setPhoneNumber(account.getPhoneNumber());
            existingAccount.setDateOfCreation(account.getDateOfCreation());
            return accountRepository.save(existingAccount);
        }
        return null;
    }

    @Override
    public ObjectNode loginAccount(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        if (existingAccount == null)
        {
            throw new LoginFailedException("Invalid username");
        }
        else
        {
            if (BCrypt.checkpw(account.getPassword(), existingAccount.getPassword()))
            {
                jsonNode.put("id", existingAccount.getId());
                jsonNode.put("name", existingAccount.getName());
            }
            else
            {
                throw new LoginFailedException("Invalid password");
            }
        }
        return jsonNode;
    }
}
