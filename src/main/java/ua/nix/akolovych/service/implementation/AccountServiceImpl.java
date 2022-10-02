package ua.nix.akolovych.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nix.akolovych.dao.AccountDao;
import ua.nix.akolovych.dao.CategoryDao;
import ua.nix.akolovych.entity.Account;
import ua.nix.akolovych.service.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    @Override
    public List<Account> findAllByUserId(Long userId) {
        return accountDao.findAllByUserId(userId);
    }


    @Override
    public void create(Account entity) {
        accountDao.create(entity);
        LOGGER_INFO.info("Account with id = " + entity.getId() + " created");
    }

    @Override
    public void update(Account entity) {
        if(accountDao.existById(entity.getId())) {
            accountDao.update(entity);
            LOGGER_INFO.info("Account with id = " + entity.getId() + " updated");
        }
        else {
            LOGGER_WARN.warn("Account with id = " + entity.getId() + " not found");
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long Id) {
        if(accountDao.existById(Id)) {
            accountDao.delete(Id);
            LOGGER_INFO.info("Account with id = " + Id + " deleted");
        }
        else {
            LOGGER_WARN.warn("Account with id = " + Id + " not found");
            throw new RuntimeException();
        }
    }

    @Override
    public Account findById(Long id) {
        Account account = accountDao.findById(id);
        if(account == null) {
            LOGGER_WARN.warn("Account with id = " + id + " not found");
            throw new RuntimeException();
        }
        LOGGER_INFO.info("Account with id = " + id + " found");
        return account;
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }
}
