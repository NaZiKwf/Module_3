package ua.nix.akolovych.dao;

import ua.nix.akolovych.entity.Account;

import java.util.List;

public interface AccountDao extends BaseEntityDao<Account> {
    List<Account> findAllByUserId(Long userId);
}
