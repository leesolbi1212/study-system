package com.study.management.mapper;

import com.study.management.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findByEmail(@Param("email") String email);
    void insertUser(User user);
    User findById(@Param("userId") Long userId);
    void updateUser(User user);
}