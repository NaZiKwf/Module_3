package ua.nix.akolovych.service.implementation;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nix.akolovych.dao.AccountDao;
import ua.nix.akolovych.dao.TransactionDao;
import ua.nix.akolovych.entity.Account;
import ua.nix.akolovych.entity.Category;
import ua.nix.akolovych.entity.Transaction;
import ua.nix.akolovych.service.CategoryService;
import ua.nix.akolovych.service.TransactionService;
import ua.nix.akolovych.utils.JdbcConnectionUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao;
    private final AccountDao accountDao;
    private  final CategoryService categoryService;

    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public TransactionServiceImpl(TransactionDao transactionDao, AccountDao accountDao, CategoryService categoryService) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
        this.categoryService = categoryService;
    }

    @Override
    public void create(Transaction entity) {
        try{
            updateAccountBalance(entity);
            transactionDao.create(entity);
            LOGGER_INFO.info("Transaction with id = " + entity.getId() + " created");
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Long Id) {

    }

    @Override
    public Transaction findById(Long id) {
        Transaction transaction = transactionDao.findById(id);
        if(transaction == null) {
            LOGGER_WARN.warn("Transaction with id = " + id + " not found");
            throw new RuntimeException();
        }
        LOGGER_INFO.info("Transaction with id = " + id + " found");
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    @Override
    public List<Transaction> findAllByAccountId(Long accountId) {
        return transactionDao.findAllByAccountId(accountId);
    }

    @Override
    public List<Transaction> findAllByCategoryId(Long categoryId) {
        return transactionDao.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Transaction> findAllByUserId(Long userId) {
        return transactionDao.findAllByUserId(userId);
    }

    @Override
    public void exportStatementByAccount(Long accountId, Instant from, Instant to, String filePath) throws SQLException {
        List <String []> records = new ArrayList<>();
        String [] header = {"Id", "Amount", "Created", "Category"};
        records.add(header);
        Connection connection = JdbcConnectionUtil.getConnection("jdbc:postgresql:module3","admin","admin");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("select * FROM Transactions WHERE account_id = %s",accountId));
        while(resultSet.next()){
            Instant instant=resultSet.getTimestamp("created").toInstant();
            if(instant.isAfter(from) && instant.isBefore(to)){
                String[] transactionString = new String[4];
                transactionString[0] = String.valueOf(resultSet.getLong("account_id"));
                transactionString[1] = String.valueOf(resultSet.getLong("amount"));
                transactionString[2] = resultSet.getTimestamp("created").toString();
                Long categoryId = resultSet.getLong("category_Id");
                Category category = categoryService.findById(categoryId);
                Boolean b = category.isIncome();
                String s = "";
                if(b){
                    s = "income";
                }
                else{
                    s = "outcome";
                }
                transactionString[3] = s;
                records.add(transactionString);
            }
        }
        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath))){
            csvWriter.writeAll(records);
            LOGGER_INFO.info("Account statement for account with id = " + accountId + " successfully exported");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void updateAccountBalance(Transaction transaction){
        if(transaction.getAmount() <= 0){
            throw new IllegalArgumentException("The transaction amount must be greater than zero");
        }
        Account account = accountDao.findById(transaction.getAccount().getId());
        long currBalance = account.getBalance();
        if(transaction.getCategory().isIncome()){
            account.setBalance(currBalance + transaction.getAmount());
        }
        else {
            if(currBalance - transaction.getAmount() < 0){
                throw new IllegalArgumentException("Insufficient funds for the operation");
            }
            else {
                account.setBalance(currBalance - transaction.getAmount());
                accountDao.update(account);
            }
        }
    }
}
