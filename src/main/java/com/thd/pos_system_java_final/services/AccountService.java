package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.account.Account;
import com.thd.pos_system_java_final.models.account.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService /*implements UserDetailsService */{

    @Autowired
    private AccountRepository repo;
//    @Autowired
//    private PasswordEncoder encoder;

    public Account getAccountByUsername(String username) {
        return repo.findByUsername(username);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Account acc = repo.findByUsername(username);
//        if (acc == null) throw new UsernameNotFoundException("No account with " + username);
//
//        return User.withUsername(acc.getUsername())
//                .password(acc.getPassword())
//                .roles(acc.getRole())
//                .disabled(acc.isActivate())
//                .build();
//    }
}
