package com.example.springboot.mybatisdemo.dao;


import com.example.springboot.mybatisdemo.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.springboot.mybatisdemo.domain.User;

import java.util.List;

/**
 * Created by liuguo on 2017/8/1.
 */
@Repository
public class UserDao {

    private SqlSession sqlSession;

    @Autowired
    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<User> findAll(){
        return sqlSession.selectList("findAll", UserMapper.class);
    }
}
