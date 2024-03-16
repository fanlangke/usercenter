package com.lqx.usercenter.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqx.usercenter.model.domain.User;
import com.lqx.usercenter.service.UserService;
import com.lqx.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lqx.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 * @author fanlangke
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-01-29 13:54:11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    UserMapper userMapper;


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

        if (userAccount == null || userPassword == null || checkPassword == null) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }

        if (userPassword.length() < 4) {
            return -1;
        }
        if (userPassword.length() > 8 || checkPassword.length() > 8) {
            return -1;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            return -1;
        }
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);

        if (matcher.find()) {
            return -1;
        }

        final String SALT = "lqx";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);

        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        if (userAccount == null || userPassword == null) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }

        if (userPassword.length() < 4) {
            return null;
        }
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);

        if (matcher.find()) {
            return null;
        }

        final String SALT = "lqx";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        for(int i=0;i<10;i++){
            System.out.println(userAccount);
            System.out.println(encryptPassword);
        }
        System.out.println(queryWrapper);
        User user = userMapper.selectOne(queryWrapper);
//        User user=null;
        if (user == null) {
            return null;
        }
        request.getSession().setAttribute(USER_LOGIN_STATE,getSafetyUser(user));
        return getSafetyUser(user);
    }
    User getSafetyUser(User user){
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());

        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUserRole(user.getUserRole());
        return safetyUser;
    }
}





