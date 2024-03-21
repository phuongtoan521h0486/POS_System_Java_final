package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AccountRole;

public interface AccountBuilder {// Apply Builder pattern
    Account build();
    AccountBuilder setUsername(String username);
    AccountBuilder setPassword(String password);
    AccountBuilder setFullName(String fullName);
    AccountBuilder setEmail(String email);
    AccountBuilder setActivate(boolean activate);
    AccountBuilder setStatus(boolean status);
    AccountBuilder setRole(AccountRole role);
    AccountBuilder setPhone(String phone);
    AccountBuilder setPicture(byte[] picture);
}
