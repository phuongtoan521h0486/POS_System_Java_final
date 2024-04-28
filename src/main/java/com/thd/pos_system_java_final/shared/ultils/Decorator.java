package com.thd.pos_system_java_final.shared.ultils;

import com.thd.pos_system_java_final.models.Account.Account;

import java.util.List;

public abstract class Decorator implements Exporter {
    protected Exporter wrapObj;

    public Decorator(Exporter wrapObj) {
        this.wrapObj = wrapObj;
    }

    @Override
    public byte[] generate(List<Account> accounts) {
        return wrapObj.generate(accounts);
    }
}
