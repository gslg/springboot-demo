package com.example.springboot.mybatisdemo.service.impl;


import com.example.springboot.mybatisdemo.dao.UserDao;
import com.example.springboot.mybatisdemo.domain.User;
import com.example.springboot.mybatisdemo.mapper.UserMapper;
import com.example.springboot.mybatisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuguo on 2017/8/1.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(long id) {
        return userMapper.getUser(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }


}
