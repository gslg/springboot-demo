package com.example.springboot.redisdemo;

import com.example.springboot.redisdemo.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by liuguo on 2017/8/2.
 */
@SpringBootApplication
public class RedisApp {

    @Autowired
    private RedisConfig redisConfig;

    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class,args);
    }

   /* @Bean
    public JedisConnectionFactory connectionFactory(){
        //172.16.166.71:7501;172.16.166.71:7502;172.16.166.71:7503
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("172.16.166.71",7501)
                .sentinel("172.16.166.71",7502)
                .sentinel("172.16.166.71",7503);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfiguration);
        jedisConnectionFactory.setPassword("123456");
        return jedisConnectionFactory;
    }*/
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
