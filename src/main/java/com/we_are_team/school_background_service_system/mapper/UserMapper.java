package com.we_are_team.school_background_service_system.mapper;

import com.we_are_team.school_background_service_system.pojo.dto.AdminLoginDTO;
import com.we_are_team.school_background_service_system.pojo.dto.ChangePasswordDTO;
import com.we_are_team.school_background_service_system.pojo.dto.UserRegisterDTO;
import com.we_are_team.school_background_service_system.pojo.entity.Student;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    void insert(UserRegisterDTO userRegisterDTO);


    @Select("select * from user where student_number=#{studentNumber} and password=#{password}")
    User getUserByStudentNumberAndPassword(User user);



    @Select("select * from user where student_number=#{studentNumber}")
    User getUserByStudentNumber(String studentNumber);

    User getUserByPasswordAndStudentNumberOrUsername(User user);

    @Select("select * from student where name=#{name} and student_number=#{studentNumber}")
    Student getStudentByNameAndStudentNumber(@Param("name") String name,@Param("studentNumber") String studentNumber);

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


//    void updateStudentByStudentNumber(String studentNumber);
}
