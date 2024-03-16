package com.lqx.usercenter.service;

import com.lqx.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author fanlangke
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-01-29 13:54:11
*/
public interface UserService extends IService<User> {
    long userRegister(String userAccount, String userPassword, String checkPassword);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
}
