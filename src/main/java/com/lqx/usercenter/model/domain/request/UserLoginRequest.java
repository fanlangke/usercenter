package com.lqx.usercenter.model.domain.request;

import lombok.Data;
import org.apache.tomcat.util.descriptor.web.SecurityRoleRef;

import java.awt.*;
import java.io.Serializable;
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -5113013006205267611L;
    private String userAccount;
    private String userPassword;
}
