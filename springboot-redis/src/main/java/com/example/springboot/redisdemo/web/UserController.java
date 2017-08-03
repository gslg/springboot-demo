package com.example.springboot.redisdemo.web;

import com.example.springboot.redisdemo.basic.AbstractSimpleSecurity;
import com.example.springboot.redisdemo.basic.CookieInterceptor;
import com.example.springboot.redisdemo.dao.RedisRepository;
import com.example.springboot.redisdemo.domain.User;
import com.example.springboot.redisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuguo on 2017/8/3.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisRepository redisRepository;

    @GetMapping
    public String list(ModelMap map){
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        map.addAttribute("userlist", userService.findAll());
        map.addAttribute("currentuser",AbstractSimpleSecurity.getName());
        return "userlist";
    }

    /**
     * 用户登陆
     * @param user
     * @param response
     * @return
     */
    @PostMapping("/login")
    public String login(User user,HttpServletResponse response){
        //先到缓存中验证:
        if(redisRepository.auth(user.getName(),user.getPassword())){
            addAuthCookie(redisRepository.addAuth(user.getName()),user.getName(),response);
            return "redirect:/users/";
        }
        //再到数据库中验证:
        if(userService.isExist(user)){
            redisRepository.addUser(user.getName(),user.getPassword());
            addAuthCookie(redisRepository.addAuth(user.getName()),user.getName(),response);
            return "redirect:/users/";
        }

        return "redirect:/toLogin/";
    }

    @GetMapping("/logout")
    public String logout(){
        String user = AbstractSimpleSecurity.getName();
        // invalidate auth
        redisRepository.deleteAuth(user);

        return "redirect:/";
    }

    private void addAuthCookie(String auth, String name, HttpServletResponse response) {
        AbstractSimpleSecurity.setUser(name, redisRepository.findUid(name));

        Cookie cookie = new Cookie(CookieInterceptor.REDIS_COOKIE, auth);
        cookie.setComment("MyApp demo");
        // cookie valid for up to 1 week
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
    }

    /**
     * 跳转到创建表单
     * @param map
     * @return
     */
    @GetMapping(value = "/create")
    public String getCreateUserForm(ModelMap map){
        map.addAttribute("user", new User());
        map.addAttribute("action", "create");
        return "userform";
    }

    @PostMapping(value = "/create")
    public String doCreate(User user){

        userService.insertByUser(user);

        //创建完成后跳转到列表页面
        return "redirect:/users/";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable Long id, ModelMap map) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        map.addAttribute("user", userService.findById(id));
        map.addAttribute("action", "update");
        return "userform";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String putUser(@ModelAttribute User user) {
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        userService.update(user);
        return "redirect:/users/";
    }


    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);

        return "redirect:/users/";
    }


}
