package com.we_are_team.school_background_service_system.controller.admin;

import com.we_are_team.school_background_service_system.mapper.UserMapper;
import com.we_are_team.school_background_service_system.pojo.dto.AdminLoginDTO;
import com.we_are_team.school_background_service_system.pojo.dto.AdminRegisterDTO;
import com.we_are_team.school_background_service_system.pojo.dto.UserLoginDTO;
import com.we_are_team.school_background_service_system.properties.JwtProperties;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminUserController")
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
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
}
