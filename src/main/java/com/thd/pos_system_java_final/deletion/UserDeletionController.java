package com.thd.pos_system_java_final.deletion;

import com.thd.pos_system_java_final.services.AccountService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDeletionController extends DeletionController {
    private AccountService accountService;

    @Override
    protected void deleteEntity(int id) {
        accountService.deleteAccount(id);
    }

    @Override
    protected String redirectToLink() {
        return "redirect:/user";
    }

}
