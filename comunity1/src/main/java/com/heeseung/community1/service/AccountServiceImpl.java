package com.heeseung.community1.service;

import com.heeseung.community1.domain.Account;
import com.heeseung.community1.dto.RegisterReqDto;
import com.heeseung.community1.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public int register(RegisterReqDto registerReqDto) throws Exception {
        Account account = registerReqDto.toEntity();
        int result = 0;

        //Validation check
        if(registerReqDto.getUsername().length() < 4 || 12 < registerReqDto.getUsername().length()){
            return 2;   //validation_username
        }else if(registerReqDto.getPassword().length() < 4 || 20 < registerReqDto.getPassword().length()){
            return 3;   //validation_password
        }

        result = accountRepository.isDuplicate(account);

        if(result == 0){
            result = accountRepository.register(account);
        }else{
            return 1;   //duplicated
        }

        if (result != 1) {
            return 100;   //sql error
        }

        return 0;
    }
}
