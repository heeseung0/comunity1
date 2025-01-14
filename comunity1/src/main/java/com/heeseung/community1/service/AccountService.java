package com.heeseung.community1.service;

import com.heeseung.community1.dto.ModifyReqDto;
import com.heeseung.community1.dto.RegisterReqDto;

public interface AccountService {
    public int register(RegisterReqDto registerReqDto) throws Exception;
    public int leave(String username) throws Exception;
    public int modify(ModifyReqDto modifyReqDto) throws Exception;
}
