package com.example.springboot.redisdemo.dao;

import com.example.springboot.redisdemo.basic.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * Created by liuguo on 2017/8/2.
 */
@Repository
public class RedisRepository {

    private static final Pattern MENTION_REGEX = Pattern.compile("@[\\w]+");

    private final StringRedisTemplate template;
    private final ValueOperations<String,String> valueOps;
    private final RedisAtomicLong postIdCounter;
    private final RedisAtomicLong userIdCounter;

    private RedisList<String> users;
    private final RedisList<String> timeline;

    /*private final HashMapper<Post,String,String> postMapper =
            new DecoratingStringHashMapper<>(new JacksonHashMapper<>(Post.class));*/

    @Autowired
    public RedisRepository(StringRedisTemplate template){
        this.template = template;
        this.valueOps = template.opsForValue();

        users = new DefaultRedisList<>(KeyUtils.users(),template);
        timeline = new DefaultRedisList<>(KeyUtils.timeline(),template);

        postIdCounter = new RedisAtomicLong(KeyUtils.globalPid(),template.getConnectionFactory());
        userIdCounter = new RedisAtomicLong(KeyUtils.globalUid(), template.getConnectionFactory());
    }

    public String addUser(String name,String password){
        String uid = String.valueOf(userIdCounter.incrementAndGet());

        BoundHashOperations<String,String,String> userOps = template.boundHashOps(KeyUtils.uid(uid));
        userOps.put("name",name);
        userOps.put("password",password);

        valueOps.set(KeyUtils.user(name),uid);
        users.addFirst(name);

        return addAuth(name);
    }

    public String addAuth(String name) {
        String uid = findUid(name);
        // add random auth key relation
        String auth = UUID.randomUUID().toString();
        valueOps.set(KeyUtils.auth(uid), auth);
        valueOps.set(KeyUtils.authKey(auth), uid);
        return auth;
    }

    public String findUid(String name) {
        return valueOps.get(KeyUtils.user(name));
    }


    public String findNameForAuth(String value) {
        String uid = valueOps.get(KeyUtils.authKey(value));
        return findName(uid);
    }

    private String findName(String uid) {
        if (!StringUtils.hasText(uid)) {
            return "";
        }
        BoundHashOperations<String, String, String> userOps = template.boundHashOps(KeyUtils.uid(uid));
        return userOps.get("name");
    }

    public boolean isUserValid(String name) {
        return template.hasKey(KeyUtils.user(name));
    }

    /**
     * 验证用户
     * @param user
     * @param pass
     * @return
     */
    public boolean auth(String user, String pass) {
        // find uid
        String uid = findUid(user);
        if (StringUtils.hasText(uid)) {
            BoundHashOperations<String, String, String> userOps = template.boundHashOps(KeyUtils.uid(uid));
            return userOps.get("password").equals(pass);
        }

        return false;
    }

    public void deleteAuth(String user) {
        String uid = findUid(user);

        String authKey = KeyUtils.auth(uid);
        String auth = valueOps.get(authKey);

        template.delete(Arrays.asList(authKey, KeyUtils.authKey(auth)));
    }
}
