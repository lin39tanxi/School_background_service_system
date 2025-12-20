package com.we_are_team.school_background_service_system.mapper;

import com.github.pagehelper.Page;
import com.we_are_team.school_background_service_system.pojo.dto.*;
import com.we_are_team.school_background_service_system.pojo.entity.Student;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import com.we_are_team.school_background_service_system.pojo.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 注册
     * @param userRegisterDTO
     */
    void insert(UserRegisterDTO userRegisterDTO);

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @Select("select * from user where student_number=#{studentNumber} and password=#{password}")
    User getUserByStudentNumberAndPassword(User user);


    /**
     * 根据学号获取用户信息
     * @param studentNumber
     * @return
     */
    @Select("select * from user where student_number=#{studentNumber}")
    User getUserByStudentNumber(String studentNumber);
    /**
     * 根据学号或用户名获取用户信息
     * @param user
     * @return
     */
    User getUserByPasswordAndStudentNumberOrUsername(User user);
    /**
     * 根据姓名和学号获取学生信息
     * @param name
     * @param studentNumber
     * @return
     */
    @Select("select * from student where name=#{name} and student_number=#{studentNumber}")
    Student getStudentByNameAndStudentNumber(@Param("name") String name,@Param("studentNumber") String studentNumber);
    /**
     * 根据用户名获取用户名
     * @param username
     * @return
     */
    @Select("select username from user where username= #{username}")
    String getUsernameByUsername(String username);

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @Select("select * from user where user_id= #{userId}")
    User getUserByUserId(@Param("userId") Integer userId);
/**
     * 根据学号获取学生信息
     * @param studentNumber
     * @return
     */
    @Select("select * from student where student_number= #{studentNumber}")
    Student getStudentByStudentNumber(@Param("studentNumber") String studentNumber);
/**
     * 根据id更新昵称
     * @param id
     * @param nickname
     */
@Update("update user set nickname=#{nickname} where user_id=#{id}")
    void updateNicknameById(@Param("id") Integer id, @Param("nickname") String nickname);

/**
     * 根据id更新密码
     * @param changePasswordDTO
     */
@Update("update user set password= #{changePasswordDTO.newPassword} where user_id= #{userId}")
    void updatePassword(@Param("userId") Integer userId, @Param("changePasswordDTO") ChangePasswordDTO changePasswordDTO);

/**
     * 根据id更新头像
     * @param
     * @param url
     */
@Update("update user set avatar_url= #{url} where user_id= #{usertId}")
    void updateAvatarUrlById(@Param("usertId") Integer userId,@Param("url") String url);
/**
     * 管理员注册
     * @param user
     */
    @Insert("insert into user(username,password,permission,registered_time,nickname) values(#{user.username},#{user.password},#{user.permission},#{user.registeredTime},#{user.nickname})")
    void adminInsert(@Param("user") User user);
    /**
     * 管理员更新密码
     * @param updatePasswordNoOldDTO
     */
    @Update("update user set password= #{updatePasswordNoOldDTO.newPassword} where user_id= #{updatePasswordNoOldDTO.userId}")
    void updateAdminPassword(@Param("updatePasswordNoOldDTO") UpdatePasswordNoOldDTO updatePasswordNoOldDTO);
    /**
     * 管理员更新权限
     * @param updatePermissionDTO
     */
    @Update("update user set permission= #{updatePermissionDTO.newPermission} where user_id= #{updatePermissionDTO.userId}")
    void updateAdminPermission(@Param("updatePermissionDTO") UpdatePermissionDTO updatePermissionDTO);
    /**
     * 管理员删除用户
     * @param userId
     */
    @Delete("delete from user where user_id= #{userId}")
    void deleteUserByAdminId(@Param("userId") Integer userId);
    /**
     * 获取所有管理员信息
     * @return
     */
    @Select("select * from user where permission != '0' ")
    Page<UserVO> getAllAdmin();


//    void updateStudentByStudentNumber(String studentNumber);
}
