package com.example.craw.controller;

import com.example.craw.domain.User;
import com.example.craw.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @GetMapping("/userall")
    public  JsonResult hello() {
        return new JsonResult(SUCCESS,"查询成功",userService.findAll());
    }

    @GetMapping("/user/{id}")
    public  JsonResult hello1(@PathVariable  Long id) {
        return new JsonResult(SUCCESS,"查询成功",userService.findById(id));
    }

    @PutMapping("/user/{id}")
    public  JsonResult hello2(@PathVariable  Long id,@RequestBody User user) {
        user.setId(id);
        return new JsonResult(SUCCESS,"修改成功",userService.updateUser(user));
    }

    /**
     * 登录控制处理
     *

     * @return
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody User user) {
        System.err.println("进入login");
        Subject subject = SecurityUtils.getSubject();
        System.err.println("进入login  2");
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        System.err.println("进入login  3");
        subject.login(token);
        return new JsonResult(SUCCESS,"成功", null);
    }

    @PostMapping("/reg")
    public JsonResult reg(@RequestBody User user) {
        System.err.println("进入reg");
        User byName = userService.findByName(user.getUsername());
        if (null!=byName){
            throw new  RuntimeException("用户名已存在");
        }
        userService.save(user);
        return new JsonResult(SUCCESS,"成功", null);
    }

    @GetMapping("/logout")
    public JsonResult logout() {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        return new JsonResult(SUCCESS,"注销成功", null);
    }
}
