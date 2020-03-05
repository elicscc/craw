package com.example.craw.controller;

import com.example.craw.domain.User;
import com.example.craw.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public  JsonResult<List<User>> hello() {
        return new JsonResult<List<User>>(SUCCESS,userService.findAll());
    }

    @GetMapping("/user/{id}")
    public  JsonResult<User> hello1(@PathVariable  Long id) {
        return new JsonResult<User>(SUCCESS,userService.findById(id));
    }

    @PutMapping("/user/{id}")
    public  JsonResult<Integer> hello2(@PathVariable  Long id,@RequestBody User user) {
        user.setId(id);
        return new JsonResult<Integer>(SUCCESS,userService.updateUser(user));
    }
//
//    @RequestMapping("/testThy")
//    public String test(Model model) {
//        model.addAttribute("name", "hei");
//        return "test";
//
//    }
//
//    @RequestMapping("/add")
//    public String add() {
//
//        return "user/add";
//
//    }
//
//    @RequestMapping("/update")
//    public String update() {
//
//        return "user/update";
//
//    }
//
//    @RequestMapping("/index")
//    public String index() {
//
//        return "/index";
//
//    }
//
//    @RequestMapping("/index2")
//    public String index2() {
//
//        return "/index2";
//
//    }
//
//    @GetMapping("/toLogin")
//    public String toLogin() {
//
//        return "/login";
//
//    }
//
//    @RequestMapping("/noAuth")
//    public String noAuth() {
//
//        return "/noAuth";
//
//    }
    /**
     * 登录控制处理
     *

     * @return
     */
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        System.err.println("进入login");
        Subject subject = SecurityUtils.getSubject();
        System.err.println("进入login  2");
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        System.err.println("进入login  3");
        try {
            subject.login(token);

            return "redirect:/testThy";
        } catch (IncorrectCredentialsException e) {
            System.err.println("出错1");
            return "login";
        } catch (UnknownAccountException e) {
            System.err.println("出错2");
            return "login";
        }

    }

//    @RequestMapping("/logout")
//    public String logout(HttpSession session, Model model) {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        model.addAttribute("msg","安全退出！");
//        return "login";
//    }
}
