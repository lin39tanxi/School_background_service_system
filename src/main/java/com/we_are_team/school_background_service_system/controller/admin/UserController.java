package com.we_are_team.school_background_service_system.controller.admin;

import com.we_are_team.school_background_service_system.mapper.UserMapper;
import com.we_are_team.school_background_service_system.pojo.dto.*;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import com.we_are_team.school_background_service_system.pojo.vo.UserVO;
import com.we_are_team.school_background_service_system.properties.JwtProperties;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminUserController")
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        String adminToken = userService.adminLogin(adminLoginDTO);
        return Result.success("管理员登录成功", adminToken);
    }
    /**
     * 管理员注册
     * @param adminRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody AdminRegisterDTO adminRegisterDTO) {
        userService.adminRegister(adminRegisterDTO);
        return Result.success("管理员注册成功");
    }

    /**
     * 管理员修改自己密码
     */
    @PostMapping("/user/changeMyPassword")
    public Result changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.updatePassword(changePasswordDTO);
        return Result.success("修改密码成功");
    }
    /**
     * 超级管理员修改管理员密码
     */
    @PostMapping("/user/changePassword")
    public Result changePassword(@RequestBody UpdatePasswordNoOldDTO updatePasswordNoOldDTO) {
        userService.updateAdminPassword(updatePasswordNoOldDTO);
        return Result.success("修改密码成功");
    }

    /**
     * 超级管理员修改管理员权限
     */
    @PostMapping("/user/updatePermission")
    public Result updatePermission(@RequestBody UpdatePermissionDTO updatePermissionDTO) {
        userService.updatePermission(updatePermissionDTO);
        return Result.success("修改权限成功");
    }
    /**
    超级管理员删除管理员
     */
    @DeleteMapping("/user/{userId}")
    public Result deleteUserById(@PathVariable Integer userId) {
        userService.deleteUserByAdminId(userId);
        return Result.success("删除管理员成功");
    }
    /**
     * 获取所有管理员信息
     */
    @GetMapping("/user/getAllUsers")
    public Result<PageResult> getAllUsers(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize)
    {
        PageResult pageResult = userService.getAllAdmin(pageNum, pageSize);
        return Result.success("获取管理员列表成功",pageResult);
    }
    /**
     * 获取管理员信息
     */
    @GetMapping("/user/getUserInfo")
    public Result<User> getUserInfo() {

        return Result.success("获取管理员信息成功",  userService.adminGetUserInfo());
    }

}
