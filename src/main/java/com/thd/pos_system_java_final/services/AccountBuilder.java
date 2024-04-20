package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AccountRole;
import org.springframework.stereotype.Component;

@Component
public class AccountBuilder implements IAccountBuilder {
    private Account account;
    public AccountBuilder() {
        this.account = new Account();
    }
    @Override
    public Account build() {
        return this.account;
    }
    @Override
    public IAccountBuilder setUsername(String username) {
        this.account.setUsername(username);
        return this;
    }
    @Override
    public IAccountBuilder setPassword(String password) {
        this.account.setPassword(password);
        return this;
    }
    @Override
    public IAccountBuilder setFullName(String fullName) {
        this.account.setFullName(fullName);
        return this;
    }
    @Override
    public IAccountBuilder setEmail(String email) {
        this.account.setEmail(email);
        return this;
    }
    @Override
    public IAccountBuilder setActivate(boolean activate) {
        this.account.setActivate(activate);
        return this;
    }
    @Override
    public IAccountBuilder setStatus(boolean status) {
        this.account.setStatus(status);
        return this;
    }
    @Override
    public IAccountBuilder setRole(AccountRole role) {
        this.account.setRole(role);
        return this;
    }
    @Override
    public IAccountBuilder setPhone(String phone) {
        this.account.setPhone(phone);
        return this;
    }
    @Override
    public IAccountBuilder setPicture(byte[] picture) {
        this.account.setPicture(picture);
        return this;
    }
}