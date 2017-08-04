package com.example.springboot.redisdemo.service.impl;

import com.example.springboot.redisdemo.dao.UserRepository;
import com.example.springboot.redisdemo.domain.User;
import com.example.springboot.redisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuguo on 2017/8/3.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User insertByUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User delete(Long id) {
        User user = findById(id);
        if(user != null)
            userRepository.delete(user.getId());
        return user;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public boolean isExist(User user) {
        Example<User> example = Example.of(user);
        return userRepository.exists(example);
    }
}
