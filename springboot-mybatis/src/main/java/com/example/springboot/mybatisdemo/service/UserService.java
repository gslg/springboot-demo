package com.example.springboot.mybatisdemo.service;


import com.example.springboot.mybatisdemo.domain.User;

import java.util.List;

/**
 * Created by liuguo on 2017/8/1.
 */
public interface UserService {
    User getUser(long id);

    List<User> findAll();
}
