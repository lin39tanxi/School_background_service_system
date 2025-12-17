package com.we_are_team.school_background_service_system.controller.user;

import com.we_are_team.school_background_service_system.pojo.dto.ChangePasswordDTO;
import com.we_are_team.school_background_service_system.pojo.dto.UserLoginDTO;
import com.we_are_team.school_background_service_system.pojo.dto.UserRegisterDTO;
import com.we_are_team.school_background_service_system.pojo.dto.UserUpdateDTO;
import com.we_are_team.school_background_service_system.pojo.vo.UserLoginVO;
import com.we_are_team.school_background_service_system.pojo.vo.UserVO;
import com.we_are_team.school_background_service_system.properties.JwtProperties;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO)
    {

        userService.register(userRegisterDTO);
        return Result.success("注册成功");

    }
/**
 *  登录
 */
   @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
       return Result.success("登录成功",userService.login(userLoginDTO));
   }
   /**
   *  获取当前用户信息
   */
   @GetMapping("/user/my")
    public Result<UserVO> getUserInfo(){
       return Result.success("获取用户信息成功",userService.getUserInfo());
    }

    @PutMapping("/user/my")
    public Result updateUserPassword(@RequestBody UserUpdateDTO userUpdateDTO){
       userService.updateUserNickname(userUpdateDTO);
       return Result.success("更新昵称成功");
    }

    @PutMapping("/user/change-password")
    public Result changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
       userService.updatePassword(changePasswordDTO);
       return Result.success("更新密码成功");
    }

    @PostMapping("/user/avatar")
        public Result<String> uploadAvatar(MultipartFile avatarFile){
           String url = userService.uploadAvatar(avatarFile);
           return Result.success("上传成功", url);
        }
    }


