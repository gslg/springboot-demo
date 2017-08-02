package com.example.springboot.mybatisdemo.web;

import com.example.springboot.mybatisdemo.domain.User;
import com.example.springboot.mybatisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuguo on 2017/8/1.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public User user(@PathVariable long id){
        User user = userService.getUser(id);
        return user;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> users(){
        return userService.findAll();
    }




}
