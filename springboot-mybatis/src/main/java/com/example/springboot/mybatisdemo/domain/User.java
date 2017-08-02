package com.example.springboot.mybatisdemo.domain;

/**
 * Created by liuguo on 2017/8/1.
 */
public class User {
    private Long id;
    private String username;
    private String usercode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    @Override
    public String toString() {
        return "{id="+id+",username="+username+",usercode="+usercode+"}";
    }
}
