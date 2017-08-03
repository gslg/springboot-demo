package com.example.springboot.redisdemo.service;

import com.example.springboot.redisdemo.domain.User;

import java.util.List;

/**
 * Created by liuguo on 2017/8/3.
 */
public interface UserService {
    List<User> findAll();

    User insertByUser(User user);

    User update(User user);

    User delete(Long id);

    User findById(Long id);

    boolean isExist(User user);
}
