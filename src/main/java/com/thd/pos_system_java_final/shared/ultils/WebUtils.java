package com.thd.pos_system_java_final.shared.ultils;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AccountRole;

public class WebUtils {
    public static boolean isAdmin(Account account) {
        return account.getRole() == AccountRole.ADMIN;
    }
}