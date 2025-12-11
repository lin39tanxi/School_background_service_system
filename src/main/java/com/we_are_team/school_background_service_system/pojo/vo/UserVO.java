package com.we_are_team.school_background_service_system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVO {
    private Integer userId;
    private String studentNumber; //学生表学号
    private String name;  //学生表姓名
    private String gender; //学生表性别
    private String className;  // 学生表班级
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate; // 学生表生日
    private String building; // 学生表宿舍楼
    private String dormitory;  // 学生表宿舍号
    private String permission;// 用户权限
    private String username; // 用户名
    private String nickname; // 用户昵称
    private String avatarUrl; // 用户头像
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime; // 用户创建时间
     // 用户更新时间
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime updatedTime;

}