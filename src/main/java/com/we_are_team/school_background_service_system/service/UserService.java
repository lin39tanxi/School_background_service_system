package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.dto.*;
import com.we_are_team.school_background_service_system.pojo.vo.UserLoginVO;
import com.we_are_team.school_background_service_system.pojo.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * 注册
     * @param userRegisterDTO
     * @return
     */
    void register(UserRegisterDTO userRegisterDTO);
/**
     * 登录
     * @param userLoginDTO
     * @return
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);
/**
     * 获取用户信息
     * @return
     */
    UserVO getUserInfo();

    /**
     * 修改用户昵称
     * @param userUpdateDTO
     */
    void updateUserNickname(UserUpdateDTO userUpdateDTO);

    /**
     * 修改密码
     * @param changePasswordDTO
     */
    void updatePassword(ChangePasswordDTO changePasswordDTO);
/**
     * 上传头像
     * @param avatar
     */
    String uploadAvatar(MultipartFile avatar);

    /**
     * 管理员登录
     * @param adminLoginDTO
     * @return
     */
    String adminLogin(AdminLoginDTO adminLoginDTO);
/**
     * 管理员注册
     * @param adminRegisterDTO
     */
    void adminRegister(AdminRegisterDTO adminRegisterDTO);
}
