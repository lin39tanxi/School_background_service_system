package com.we_are_team.school_background_service_system.controller.user;

import com.we_are_team.school_background_service_system.pojo.dto.ChangePasswordDTO;
import com.we_are_team.school_background_service_system.pojo.dto.UserLoginDTO;
import com.we_are_team.school_background_service_system.pojo.dto.UserRegisterDTO;
import com.we_are_team.school_background_service_system.pojo.dto.UserUpdateDTO;
import com.we_are_team.school_background_service_system.pojo.entity.ApplicationForm;
import com.we_are_team.school_background_service_system.pojo.vo.UserLoginVO;
import com.we_are_team.school_background_service_system.pojo.vo.UserVO;
import com.we_are_team.school_background_service_system.properties.JwtProperties;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.UserService;
import com.we_are_team.school_background_service_system.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

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

    /**
     *  更新用户昵称
     */
    @PutMapping("/user/my")
    public Result updateUserPassword(@RequestBody UserUpdateDTO userUpdateDTO){
       userService.updateUserNickname(userUpdateDTO);
       return Result.success("更新昵称成功");
    }

    /**
     *  更新用户密码
     */
    @PutMapping("/user/change-password")
    public Result changePassword(@RequestBody ChangePasswordDTO changePasswordDTO,HttpSession session){
        try {
            String verifyCode = (String) session.getAttribute("verifyCode");
            if (!verifyCode.equals(changePasswordDTO.getVerifyCode())) {
                return Result.error("验证码错误");
            }
        }catch (Exception e){
            throw new RuntimeException("验证码错误");
        }
       userService.updatePassword(changePasswordDTO);
       return Result.success("更新密码成功");
    }
    /**
     *  上传用户头像
     */
    @PostMapping("/user/avatar")
        public Result<String> uploadAvatar(MultipartFile avatarFile){
           String url = userService.uploadAvatar(avatarFile);
           return Result.success("上传成功", url);
        }

    /**
     *  获取验证码
     */
    @GetMapping("/generateVerifyCode")
        public void generateVerifyCode(HttpSession  session, HttpServletResponse response)throws IOException {
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            session.setAttribute("verifyCode",verifyCode);
            VerifyCodeUtils.outputImage(220,60,response.getOutputStream(),verifyCode);
            response.setHeader("Cache-Control", "no-store");
            response.setContentType("image/jpeg");
            log.info("生成的验证码为:{}",session.getAttribute("verifyCode"));
        }

//        获取申请状态
        @GetMapping("/user/getApplicationForm")
        public Result<List<ApplicationForm>> getApplicationForm(){
           return Result.success("获取申请状态成功",userService.getApplicationForm());

        }
//        修改手机号
    @PutMapping("/user/changePhone")
    public Result changeMyPhone(String newPhone)
    {
        userService.changeMyPhone(newPhone);
        return Result.success("更换手机号成功!");
    }

    }


