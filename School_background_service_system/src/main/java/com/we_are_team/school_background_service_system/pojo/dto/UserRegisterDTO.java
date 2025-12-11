package com.we_are_team.school_background_service_system.pojo.dto;

import lombok.Data;

/**
 * 用户注册数据传输对象
 */
@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String nickname;
    private String studentNumber;
    private String name;

//    private String dormitoryAddress;
}