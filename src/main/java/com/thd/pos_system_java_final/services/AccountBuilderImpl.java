package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AccountRole;
import org.springframework.stereotype.Component;

@Component
public class AccountBuilderImpl implements  AccountBuilder{// Apply Builder pattern
    private String username;
    private String password;
    private String fullName;
    private String email;
    private boolean activate;
    private boolean status;
    private AccountRole role;
    private String phone;
    private byte[] picture;

    @Override
    public Account build() {
        Account account = new Account();
        account.setUsername(this.username);
        account.setPassword(this.password);
        account.setFullName(this.fullName);
        account.setEmail(this.email);
        account.setActivate(this.activate);
        account.setStatus(this.status);
        account.setRole(this.role);
        account.setPhone(this.phone);
        account.setPicture(this.picture);

        return account;
    }

    @Override
    public AccountBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public AccountBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public AccountBuilder setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public AccountBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public AccountBuilder setActivate(boolean activate) {
        this.activate = activate;
        return this;
    }

    @Override
    public AccountBuilder setStatus(boolean status) {
        this.status = status;
        return this;
    }

    @Override
    public AccountBuilder setRole(AccountRole role) {
        this.role = role;
        return this;
    }

    @Override
    public AccountBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public AccountBuilder setPicture(byte[] picture) {
        this.picture = picture;
        return this;
    }
}
