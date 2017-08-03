package com.example.springboot.redisdemo.dao;

import com.example.springboot.redisdemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by liuguo on 2017/8/3.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
