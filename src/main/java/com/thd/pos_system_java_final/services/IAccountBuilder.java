package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AccountRole;

public interface IAccountBuilder {
    Account build();
    IAccountBuilder setUsername(String username);
    IAccountBuilder setPassword(String password);
    IAccountBuilder setFullName(String fullName);
    IAccountBuilder setEmail(String email);
    IAccountBuilder setActivate(boolean activate);
    IAccountBuilder setStatus(boolean status);
    IAccountBuilder setRole(AccountRole role);
    IAccountBuilder setPhone(String phone);
    IAccountBuilder setPicture(byte[] picture);
}
