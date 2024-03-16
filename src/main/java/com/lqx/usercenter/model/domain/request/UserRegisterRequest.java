package com.lqx.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -3292360943713981658L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;

}
