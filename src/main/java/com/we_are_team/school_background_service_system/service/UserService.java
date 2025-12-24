package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.dto.*;
import com.we_are_team.school_background_service_system.pojo.entity.ApplicationForm;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import com.we_are_team.school_background_service_system.pojo.vo.UserLoginVO;
import com.we_are_team.school_background_service_system.pojo.vo.UserVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    /**
     * 注册
     * 
     * @param userRegisterDTO
     * @return
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 登录
     * 
     * @param userLoginDTO
     * @return
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);

    /**
     * 获取用户信息
     * 
     * @return
     */
    UserVO getUserInfo();

    /**
     * 修改用户昵称
     * 
     * @param nickname
     */
    void updateUserNickname(String nickname);

    /**
     * 修改密码
     * 
     * @param changePasswordDTO
     */
    void updatePassword(ChangePasswordDTO changePasswordDTO);

    /**
     * 上传头像
     * 
     * @param avatar
     */
    String uploadAvatar(MultipartFile avatar);

    /**
     * 管理员登录
     * 
     * @param adminLoginDTO
     * @return
     */
    String adminLogin(AdminLoginDTO adminLoginDTO);

    /**
     * 管理员注册
     * 
     * @param adminRegisterDTO
     */
    void adminRegister(AdminRegisterDTO adminRegisterDTO);

    /**
     * 超级管理员修改密码
     * 
     * @param updatePasswordNoOldDTO
     */
    void updateAdminPassword(UpdatePasswordNoOldDTO updatePasswordNoOldDTO);

    /**
     * 超级管理员修改权限
     * 
     * @param updatePermissionDTO
     */
    void updatePermission(UpdatePermissionDTO updatePermissionDTO);

    /**
     * 删除管理员
     * 
     * @param userId
     */
    void deleteUserByAdminId(Integer userId);

    /**
     * 获取所有管理员
     * 
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult getAllAdmin(Integer pageNum, Integer pageSize);

    /**
     * 获取管理员信息
     * 
     * @return
     */
    User adminGetUserInfo();

    /**
     * 获取申请信息
     * 
     * @return
     */
    List<ApplicationForm> getApplicationForm();

    /**
     * 修改手机号
     * 
     * @param newPhone
     */
    void changeMyPhone(String newPhone);

    PageResult getUserList(Integer pageNum, Integer pageSize, String keyword);

    void changeStudentPassword(ChangeStudentPasswordDTO changeStudentPasswordDTO);
}
