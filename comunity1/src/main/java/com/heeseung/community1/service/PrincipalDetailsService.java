package com.heeseung.community1.service;

import com.heeseung.community1.domain.Account;
import com.heeseung.community1.repository.AccountRepository;
import com.heeseung.community1.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = null;

        try{
            account = accountRepository.getAccount(username);
        }catch (Exception e){
            log.error("Username Not Found Error : " + e.getMessage());
        }

        if(account == null){
            throw new UsernameNotFoundException("account was wrong");
        }

        return new PrincipalDetails(account);
    }
}
