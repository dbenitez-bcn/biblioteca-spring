package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountEmail;
import com.example.biblioteca.modules.accounts.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BibliotecaUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountMaybe = accountRepository.getByEmail(new AccountEmail(username));
        if (accountMaybe.isPresent()) {
            Account account = accountMaybe.get();
            return new User(
                    account.getEmail().getValue(),
                    account.getPassword().getValue(),
                    new ArrayList<>()
            );
        }
        throw new UsernameNotFoundException(username + " not found.");
    }
}
