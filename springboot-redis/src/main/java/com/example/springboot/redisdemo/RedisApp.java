package com.example.springboot.redisdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by liuguo on 2017/8/2.
 */
@SpringBootApplication
public class RedisApp  extends SpringBootServletInitializer {

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
     * 打包成war包.
     * 由于配置类是自己，所以这里不需要覆盖configure()方法.
     * Customize the application or call application.sources(...) to add sources
     * Since our example is itself a @Configuration
     * class (via @SpringBootApplication)
     * we actually don't need to override this method.
     * @link https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#howto-create-a-deployable-war-file
     * @param builder
     * @return
     */
   /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RedisApp.class);
    }*/
}
