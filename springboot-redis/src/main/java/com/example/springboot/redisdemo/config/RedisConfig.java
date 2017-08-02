package com.example.springboot.redisdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by liuguo on 2017/8/2.
 */
@ConfigurationProperties(prefix = "spring.myredis")
@Component
public class RedisConfig {
    private String master;
    private String[] nodes;
    private String password;

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String[] getNodes() {
        return nodes;
    }

    public void setNodes(String[] nodes) {
        this.nodes = nodes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{master="+master+",password="+password+",nodes="+ Arrays.toString(nodes)+"}";
    }
}
