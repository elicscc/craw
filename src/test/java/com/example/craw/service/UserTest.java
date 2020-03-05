//package com.example.craw.service;
//
//import com.example.craw.domain.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserTest {
//
//    @Autowired
//    private UserService userServicee;
//    @Test
//    public void  s(){
//        List<User> users=userServicee.findAll();
//        System.err.println(users);
//    }
//
//    @Test
//    public void  s2(){
//        User user= userServicee.findById(1L);
//        System.err.println(user);
//    }
//    @Test
//    public void  s3(){
//        User user= userServicee.findById(1L);
//        System.err.println("修改前的用户"+user);
//        user.setAge(73);
//      Integer i=  userServicee.updateUser(user);
//        System.err.println( i);
//        user= userServicee.findById(1L);
//        System.err.println("修改后的用户"+user);
//    }
//}
