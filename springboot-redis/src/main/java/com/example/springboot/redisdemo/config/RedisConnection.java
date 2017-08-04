package com.example.springboot.redisdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by liuguo on 2017/8/4.
 */

@Configuration
public class RedisConnection {

    @Autowired
    private RedisConfig redisConfig;

    /**
     * 兼容单节点、集群、哨兵
     * @return
     */
    @Bean
    public JedisConnectionFactory connectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = null;

        if(StringUtils.hasText(redisConfig.getMaster())){
            //哨兵模式
            jedisConnectionFactory = buildWithSentinel();
        }else if(redisConfig.getNodes().length>1){
            //集群
            jedisConnectionFactory = buildWithCluster();
        }else {
            //单机
            jedisConnectionFactory = buildWithSingle();
        }

        if(StringUtils.hasText(redisConfig.getPassword())){
            jedisConnectionFactory.setPassword(redisConfig.getPassword());
        }

        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    /**
     * 哨兵模式
     * @return
     */
    private JedisConnectionFactory buildWithSentinel(){
        String[] nodes = redisConfig.getNodes();
        Set<String> ipAndports = new HashSet(Arrays.asList(nodes));

        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration(redisConfig.getMaster(),ipAndports);

        return new JedisConnectionFactory(sentinelConfiguration);
    }

    /**
     * 集群模式
     * @return
     */
    private JedisConnectionFactory buildWithCluster(){
        String[] nodes = redisConfig.getNodes();
        Set<String> ipAndports = new HashSet(Arrays.asList(nodes));

        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(ipAndports);
        return new JedisConnectionFactory(clusterConfiguration);
    }

    /**
     * 单机模式
     * @return
     */
    private JedisConnectionFactory buildWithSingle(){
        String[] nodes = redisConfig.getNodes();


        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        String[] node = nodes[0].split(":");
        jedisConnectionFactory.setHostName(node[0]);
        jedisConnectionFactory.setPort(Integer.parseInt(node[1]));

        return jedisConnectionFactory;
    }

}
