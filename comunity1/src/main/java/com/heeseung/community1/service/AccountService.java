package com.heeseung.community1.service;

import com.heeseung.community1.dto.RegisterReqDto;

public interface AccountService {
    public int register(RegisterReqDto registerReqDto)throws Exception;
}
