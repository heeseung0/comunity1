package com.heeseung.community1.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogRepository {
    public int loggingAccount (String username, String message);
}
