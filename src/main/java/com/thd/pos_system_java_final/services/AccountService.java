package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AccountRepository;
import com.thd.pos_system_java_final.models.Account.AccountRole;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository repo;

    @Autowired
    private IAccountBuilder accountBuilder;

    public Account getAccountByEmail(String email) {
        return repo.findAccountByEmail(email);
    }

    // Apply Builder pattern
    public void createAccount(Account account) {
        String username = account.getEmail().split("@")[0];

        Account newAccount = accountBuilder.setEmail(account.getEmail())
                .setUsername(username)
                .setPassword(BCrypt.hashpw(username, BCrypt.gensalt(10)))
                .setFullName(account.getFullName())
                .setRole(AccountRole.EMPLOYEE)
                .setActivate(false)
                .setStatus(true)
                .setPicture(getDefaultImageBytes())
                .setPhone("")
                .build();
        repo.save(newAccount);
    }
    // Apply Builder pattern
    public Account getAccountByUsername(String username) {
        return repo.findByUsername(username);
    }
    public void deleteAccount(int id) {
        repo.deleteById(id);
    }

    public void updateAccount(Account account) {
        repo.save(account);
    }

    public Account getAccountById(int id) {
        return repo.findAccountByAccountId(id);
    }

    public List<Account> getAccountByEmployeeRole() {
        return repo.findAllByRole(AccountRole.EMPLOYEE);
    }

    private byte[] getDefaultImageBytes() {
        try {
            Resource resource = new ClassPathResource("static/images/avatar-default.png");
            return Files.readAllBytes(resource.getFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
