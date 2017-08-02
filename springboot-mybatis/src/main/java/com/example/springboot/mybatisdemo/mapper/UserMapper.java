package com.example.springboot.mybatisdemo.mapper;


import org.apache.ibatis.annotations.Mapper;
import com.example.springboot.mybatisdemo.domain.User;

import java.util.List;

/**
 * Created by liuguo on 2017/8/1.
 */
@Mapper
public interface UserMapper {
    User getUser(long id);

    List<User> findAll();
}
