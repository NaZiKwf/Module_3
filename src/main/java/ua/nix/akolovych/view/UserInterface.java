package ua.nix.akolovych.view;


import java.sql.SQLException;
import java.time.temporal.ChronoUnit;

import ua.nix.akolovych.dao.implementation.AccountDaoImpl;
import ua.nix.akolovych.dao.implementation.CategoryDaoImpl;
import ua.nix.akolovych.dao.implementation.TransactionDaoImpl;
import ua.nix.akolovych.dao.implementation.UserDaoImpl;
import ua.nix.akolovych.entity.Account;
import ua.nix.akolovych.entity.Category;
import ua.nix.akolovych.entity.Transaction;
import ua.nix.akolovych.entity.User;
import ua.nix.akolovych.service.AccountService;
import ua.nix.akolovych.service.CategoryService;
import ua.nix.akolovych.service.TransactionService;
import ua.nix.akolovych.service.UserService;
import ua.nix.akolovych.service.implementation.AccountServiceImpl;
import ua.nix.akolovych.service.implementation.CategoryServiceImpl;
import ua.nix.akolovych.service.implementation.TransactionServiceImpl;
import ua.nix.akolovych.service.implementation.UserServiceImpl;

import java.time.Instant;
import java.util.HashSet;

public class UserInterface {
    AccountService accountService = new AccountServiceImpl(new AccountDaoImpl());
    UserService userService = new UserServiceImpl(new UserDaoImpl());
    TransactionService transactionService = new TransactionServiceImpl(new TransactionDaoImpl(),new AccountDaoImpl(),
            new CategoryServiceImpl(new CategoryDaoImpl()));
    CategoryService categoryService = new CategoryServiceImpl(new CategoryDaoImpl());

    public void run() throws SQLException {
        Category category = new Category("salary",true,new HashSet<>());
        categoryService.create(category);
        User user = new User("Oleg","Ivan", 35, new HashSet<>(), Instant.now());
        userService.create(user);
        Account account = new Account(user,0l,new HashSet<>());
        accountService.create(account);
        Transaction transaction = new Transaction(1500l,Instant.now(),account,category);
        transactionService.create(transaction);
        System.out.println("Creating new operation: ");
        exportUserOperations();
        System.out.println("Export operations to file: ");

    }

    private void exportUserOperations() throws SQLException {
        User user = userService.findAll().get(0);
        Account account = accountService.findAllByUserId(user.getId()).get(0);
        transactionService.exportStatementByAccount(account.getId(),Instant.now().minus(1, ChronoUnit.DAYS),
                Instant.now().plus(1, ChronoUnit.DAYS), "src/main/resources/export.csv");

    }
}
