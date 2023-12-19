package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.account.Account;
import com.thd.pos_system_java_final.models.account.AccountRepository;
import com.thd.pos_system_java_final.models.account.AccountRole;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
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

    public Account getAccountByEmail(String email) {
        return repo.findAccountByEmail(email);
    }

    public Account createAccount(Account account) {
        String username = account.getEmail().split("@")[0];
        String hashedPassword = BCrypt.hashpw(username, BCrypt.gensalt(10));
        account.setRole(AccountRole.EMPLOYEE);
        account.setActivate(false);
        account.setStatus(true);
        account.setUsername(username);
        account.setPassword(hashedPassword);
        return repo.save(account);
    }



    public Account getAccountByUsername(String username) {
        return repo.findByUsername(username);
    }

    public List<Account> getAllAccount() {
        return repo.findAll();
    }

    public void deleteAccount(int id) {
        repo.deleteById(id);
    }

    public void updateAccount(Account account) {
        repo.save(account);
    }

    public Account getAccountById(int id) {
        return repo.findAccountByAccountId(id);
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
