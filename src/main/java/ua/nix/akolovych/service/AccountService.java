package ua.nix.akolovych.service;

import ua.nix.akolovych.entity.Account;
import ua.nix.akolovych.entity.BaseEntity;

import java.util.List;

public interface AccountService extends BaseEntityService<Account> {
    List<Account> findAllByUserId(Long userId);
}
