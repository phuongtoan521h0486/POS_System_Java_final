package com.thd.pos_system_java_final.deletion;

import org.springframework.beans.factory.annotation.Autowired;

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
