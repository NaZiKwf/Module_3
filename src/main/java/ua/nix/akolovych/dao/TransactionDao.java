package ua.nix.akolovych.dao;

import ua.nix.akolovych.entity.Transaction;

import java.util.List;

public interface TransactionDao extends BaseEntityDao<Transaction> {
    List<Transaction> findAllByAccountId(Long accountId);
    List<Transaction> findAllByCategoryId(Long categoryId);
    List<Transaction> findAllByUserId(Long userId);
}
