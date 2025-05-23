package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.Account;
import com.example.demo.models.Authority;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.util.constants.Roles;

@Service
public class AccountService implements UserDetailsService{
    @Value("${app.default-photo-path}")
    private String photo_prefix;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Account save(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if(account.getRole()==null){
            account.setRole(Roles.USER.getRole());
        }
        if(account.getPhoto()==null){
            String path=photo_prefix;
            account.setPhoto(path);
        }
        return accountRepository.save(account);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount=accountRepository.findByEmailIgnoreCase(username);
        if (!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        Account account = optionalAccount.get();
        List<SimpleGrantedAuthority> grantedAuthority=new ArrayList<>();
        grantedAuthority.add(new SimpleGrantedAuthority(account.getRole()));
        for(Authority _auth:account.getAuthorities()){
            grantedAuthority.add(new SimpleGrantedAuthority(_auth.getName()));
        }

        // Assuming Account implements UserDetails or you map it manually
        return new User(
            account.getEmail(), 
            account.getPassword(),grantedAuthority  // Or you can provide custom roles
        );
    }
    public Optional<Account> findOneByEmail(String email){
        return accountRepository.findByEmailIgnoreCase(email);
    }
    public Optional<Account> findById(long id){
        return accountRepository.findById(id);    
    }
    // public Optional<Account> findByToken(String token){
    //     return accountRepository.findByToken(token);
    // }
    public Optional<Account> findByPasswordResetToken(String token) {
    return accountRepository.findByPasswordResetToken(token);
}
}
