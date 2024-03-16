package com.lqx.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lqx.usercenter.model.domain.User;
import com.lqx.usercenter.model.domain.request.UserLoginRequest;
import com.lqx.usercenter.model.domain.request.UserRegisterRequest;
import com.lqx.usercenter.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lqx.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.lqx.usercenter.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest==null){
            return null;
        }
        String userAccount=userRegisterRequest.getUserAccount();
        String userPassword=userRegisterRequest.getUserPassword();
        String checkPassword=userRegisterRequest.getCheckPassword();
        if(userAccount==null||userPassword==null||checkPassword==null){
            return null;
        }
        return userService.userRegister(userAccount,userPassword,checkPassword);
    }

    @PostMapping("login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest==null){
            return null;
        }
        String userAccount=userLoginRequest.getUserAccount();
        String userPassword=userLoginRequest.getUserPassword();
        if(userAccount==null||userPassword==null){
            return null;
        }
        return userService.userLogin(userAccount,userPassword,request);
    }
    @PostMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest request){
        if(!isAdmin(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(username!=null){
            queryWrapper.like("username",username);
        }
        List<User> userList=userService.list(queryWrapper);
        return userList.stream().map(user -> {
            user.setUserPassword(null);
            return user;
        }).collect(Collectors.toList());
    }

    @PostMapping("delete")
    public boolean deleteUser(@RequestBody long id,HttpServletRequest request){
        if(!isAdmin(request)){
            return false;
        }
        if(id<=0){
            return false;
        }
        return userService.removeById(id);
    }
    public boolean isAdmin(HttpServletRequest request){
        Object userobj =request.getSession().getAttribute(USER_LOGIN_STATE);
        User user =(User) userobj;
        return user!=null&&user.getUserRole()==ADMIN_ROLE;
    }
}
