package com.lqx.usercenter.service;

import com.lqx.usercenter.model.domain.User;
import com.lqx.usercenter.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@SpringBootTest
public class UserServiceTest {

    @Resource
    private  UserService userService;
    @Test
    void testAddUser(){
        User user=new User();
        user.setUsername("dogcsa");
        user.setUserAccount("123456");
        user.setAvatarUrl("https://i1.hdslb.com/bfs/face/7bd5e0f73af5d232ecd0314a130093370e0a7853.jpg@240w_240h_1c_1s_!web-avatar-nav.avif");
        user.setGender(0);
        user.setUserPassword("456");
        user.setEmail("123");
        user.setPhone("456");
        boolean result= userService.save(user);
        System.out.println(user.getId());
        Assertions.assertEquals(true,result);
    }

    @Test
    void userRegister() {

        String userAccount="lqxs";
        String userPassword="";
        String checkPassword="123456";
        long result=userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount="lqx";
        result=userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount="lqxs";
        userPassword="123456";
        result=userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount="lqx s";
        userPassword="12345678";
        result=userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userPassword="123456789";
        result=userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount="doglqx";
        userPassword="12345678";
        result=userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount="lqxs";
        result=userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);



    }
}