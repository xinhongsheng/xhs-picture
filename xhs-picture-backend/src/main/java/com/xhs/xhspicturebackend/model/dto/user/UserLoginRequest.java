package com.xhs.xhspicturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-09-14
 * @Description:用户登录请求
 * @Version: 1.0
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -3871876020719686166L;
    private String userAccount;
    private String userPassword;


}
