package com.heeseung.community1.service;

import com.heeseung.community1.domain.Account;
import com.heeseung.community1.dto.ModifyReqDto;
import com.heeseung.community1.dto.RegisterReqDto;
import com.heeseung.community1.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public int register(RegisterReqDto registerReqDto) throws Exception {
        Account account = registerReqDto.toEntity();

        //Validation check
        if (registerReqDto.getUsername().length() < 4 || 12 < registerReqDto.getUsername().length()) {
            return 1;   //validation_username
        } else if (registerReqDto.getPassword().length() < 4 || 20 < registerReqDto.getPassword().length()) {
            return 2;   //validation_password
        }

        if (accountRepository.isDuplicate(account) != 0) {
            return 3;   //duplicated
        } else {
            if (accountRepository.register(account) == 1) {
                return 0;
            } else {
                return 100; //sql error
            }
        }
    }

    @Override
    public int leave(String username) throws Exception {
        return accountRepository.leave(username);
    }

    @Override
    public int modify(ModifyReqDto modifyReqDto) throws Exception {
        String pd_now = modifyReqDto.getPassword_now();
        Account account = accountRepository.getAccount(modifyReqDto.getUsername());


        //Validation check
        if (!BCrypt.checkpw(pd_now, account.getPassword())) {
            return 1;   //validation_password_now
        } else if (modifyReqDto.getPassword_new().length() < 4 || 20 < modifyReqDto.getPassword_new().length()) {
            return 2;   //validation_password_new
        }

        accountRepository.modify(modifyReqDto.toEntity());

        return 0;
    }
}
