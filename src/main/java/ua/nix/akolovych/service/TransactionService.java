package ua.nix.akolovych.service;

import ua.nix.akolovych.entity.Transaction;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public interface TransactionService extends BaseEntityService<Transaction> {
    List<Transaction> findAllByAccountId(Long accountId);
    List<Transaction> findAllByCategoryId(Long categoryId);
    List<Transaction> findAllByUserId(Long userId);
    void exportStatementByAccount(Long accountId, Instant from, Instant to, String filePath) throws SQLException;
}
