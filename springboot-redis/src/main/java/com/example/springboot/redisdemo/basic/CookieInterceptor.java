package com.example.springboot.redisdemo.basic;

import com.example.springboot.redisdemo.dao.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuguo on 2017/8/2.
 */
@Component
public class CookieInterceptor extends HandlerInterceptorAdapter {

    public static final String REDIS_COOKIE = "rediscookie";

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       Cookie[] cookies = request.getCookies();

        if(!ObjectUtils.isEmpty(cookies)){
            for(Cookie cookie : cookies){
                if(REDIS_COOKIE.equals(cookie.getName())){
                    String auth = cookie.getValue();
                    String name = redisRepository.findNameForAuth(auth);
                    if(name != null){
                        String uid = redisRepository.findUid(name);
                        AbstractSimpleSecurity.setUser(name, uid);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AbstractSimpleSecurity.clean();
    }
}
