package com.thd.pos_system_java_final.models.Account;

import com.thd.pos_system_java_final.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationParams {
    public LoginRequest loginRequest;
    public Model model;
    public HttpSession session;
    public AccountService accountService;
}
