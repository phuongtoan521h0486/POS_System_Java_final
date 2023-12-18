package com.thd.pos_system_java_final.models.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findByUsername(String username);
    List<Account> findAll();
}
