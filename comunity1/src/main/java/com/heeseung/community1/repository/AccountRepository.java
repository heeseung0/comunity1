package com.heeseung.community1.repository;

import com.heeseung.community1.domain.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {
    public int register(Account account) throws Exception;
    public int isDuplicate(Account account) throws Exception;
    public Account getAccount(String username) throws Exception;
    public int leave(String username) throws Exception;
    public int modify(Account account) throws Exception;
}
