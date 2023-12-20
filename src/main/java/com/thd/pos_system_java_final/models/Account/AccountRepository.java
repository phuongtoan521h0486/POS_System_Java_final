package com.thd.pos_system_java_final.models.Account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findAccountByAccountId(int id);
    Account findByUsername(String username);
    Account findAccountByEmail(String email);
    List<Account> findAll();

}
