package com.thd.pos_system_java_final.shared.ultils;

import com.thd.pos_system_java_final.models.Account.Account;

import java.util.List;

public interface Exporter {
    String getExtensionPart();

    byte[] generate(List<Account> accounts);
}
