package com.we_are_team.school_background_service_system.service.Impl;

import com.we_are_team.school_background_service_system.context.BaseContext;
import com.we_are_team.school_background_service_system.mapper.UserMapper;
import com.we_are_team.school_background_service_system.pojo.dto.*;
import com.we_are_team.school_background_service_system.pojo.entity.Student;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import com.we_are_team.school_background_service_system.pojo.vo.UserLoginVO;
import com.we_are_team.school_background_service_system.pojo.vo.UserVO;
import com.we_are_team.school_background_service_system.properties.JwtProperties;

import com.we_are_team.school_background_service_system.service.UserService;
import com.we_are_team.school_background_service_system.utils.AliOssUtil;
import com.we_are_team.school_background_service_system.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtProperties jwtProperties;
      @Autowired
      private UserMapper userMapper;
      @Autowired
      private AliOssUtil aliOssUtil;


    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        if(userRegisterDTO.getStudentNumber() == null || userRegisterDTO.getStudentNumber().equals("")
        || userRegisterDTO.getName() == null || userRegisterDTO.getName().equals("")
        || userRegisterDTO.getPassword() == null || userRegisterDTO.getPassword().equals("")
        || userRegisterDTO.getNickname() == null || userRegisterDTO.getNickname().equals("")
        || userRegisterDTO.getUsername() == null || userRegisterDTO.getUsername().equals("")){
            throw new RuntimeException("请填写完整信息");
        }
        String name = userRegisterDTO.getName();
        String studentNumber = userRegisterDTO.getStudentNumber();
        String username = userRegisterDTO.getUsername();
        if(userMapper.getUsernameByUsername(username) != null){
            throw new RuntimeException("用户名已存在");
        }
//        查找学校里面是否有这学生
        Student student = userMapper.getStudentByNameAndStudentNumber(name,studentNumber);
        if(student == null || student.equals("")){
            throw new RuntimeException("学生学号或姓名错误");
        }

//        查找user表是否已经注册这个学号
        User user =  userMapper.getUserByStudentNumber(userRegisterDTO.getStudentNumber());
        if(user != null){
            throw new RuntimeException("该学号已经注册过了");
        }
        userMapper.insert(userRegisterDTO);

    }

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        User user = new User();
        user.setStudentNumber(userLoginDTO.getStudentNumber());
        user.setPassword(userLoginDTO.getPassword());
        user.setUsername(userLoginDTO.getUsername());
        UserLoginVO userLoginVO = new UserLoginVO();
        user = userMapper.getUserByPasswordAndStudentNumberOrUsername(user);
        log.info("用户登录信息：{}",user);
        if(user == null || user.equals("")){
            throw new RuntimeException("账号或密码错误");
        }
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getUserId());
        log.info("claims:{}", claims);
        String token =  JwtUtil.createJwt(jwtProperties.getUserSecretkey(), jwtProperties.getUserTtl(),claims);
        userLoginVO.setToken(token);
        return userLoginVO;
    }

    /**
     * 获取用户信息
     * @return UserVO
     */
    @Override
    public UserVO getUserInfo() {
        int userId = BaseContext.getCurrentId();
        User user = userMapper.getUserByUserId(userId);
        Student student = userMapper.getStudentByStudentNumber(user.getStudentNumber());
        UserVO userVO = new UserVO();
        userVO.setUserId(user.getUserId());
        userVO.setStudentNumber(student.getStudentNumber());
        userVO.setName(student.getName());
        userVO.setGender(student.getGender());
        userVO.setClassName(student.getClassName());
        userVO.setBirthDate(student.getBirthDate());
        userVO.setBuilding(student.getBuilding());
        userVO.setDormitory(student.getDormitory());
        userVO.setPermission(user.getPermission());
        userVO.setUsername(user.getUsername());
        userVO.setNickname(user.getNickname());
        userVO.setAvatarUrl(user.getAvatarUrl());
        userVO.setCreatedTime(user.getRegisteredTime());
        userVO.setUpdatedTime(user.getUpdatedTime());
        return userVO;
    }

    /**
     * 修改用户昵称
     * @param userUpdateDTO
     */

    @Override
    public void updateUserNickname(UserUpdateDTO userUpdateDTO) {
        Integer id = BaseContext.getCurrentId();
        String nickname = userUpdateDTO.getNickname();
        if(nickname == null || nickname.equals("")){
            throw new RuntimeException("用户昵称不能为空");
        }
        userMapper.updateNicknameById(id,nickname);

    }
/**
     * 修改用户密码
     * @param changePasswordDTO
     */
    @Override
    public void updatePassword(ChangePasswordDTO changePasswordDTO) {
//        查找密码对比数据
        Integer userId = BaseContext.getCurrentId();
        User user = userMapper.getUserByUserId(userId);
        if(!user.getPassword().equals(changePasswordDTO.getOldPassword())){
            log.info("原来密码：{}", user.getPassword());
            log.info("输入的旧密码：{}", changePasswordDTO.getOldPassword());
            throw new RuntimeException("旧密码不正确");
        }
        if(changePasswordDTO.getNewPassword().equals("")){
            throw new RuntimeException("新密码不能为空");
        }
       userMapper.updatePassword(userId,changePasswordDTO);

    }

    @Override
    public String uploadAvatar(MultipartFile avatarFile) {
        /**
         * 上传头像
         */
        String originalFilename = avatarFile.getOriginalFilename();
        String objectName =  originalFilename.substring(originalFilename.lastIndexOf("."));
        String obj = UUID.randomUUID().toString() + objectName;
        String url = null;
        try {
            url = aliOssUtil.upload(avatarFile.getBytes(), obj);
            userMapper.updateAvatarUrlById(BaseContext.getCurrentId(),url);
            return url;
        } catch (IOException e) {
            throw new RuntimeException("上传失败");
        }
    }

    /**
     * 管理员登录
     * @param adminLoginDTO
     * @return
     */
    @Override
    public String adminLogin(AdminLoginDTO adminLoginDTO) {
        User user = new User();
        user.setUsername(adminLoginDTO.getUsername());
        user.setPassword(adminLoginDTO.getPassword());
        user = userMapper.getUserByPasswordAndStudentNumberOrUsername(user);
        log.info("用户登录信息：{}",user);
//        没有这个用户
        if(user == null || user.equals("")){
            throw new RuntimeException("账号或密码错误");
        }
        if(StringUtils.equals(user.getPermission(),"0")){
            throw new RuntimeException("你没有权限登录管理端");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        log.info("claims:{}", claims);
        String adminToken = JwtUtil.createJwt(jwtProperties.getAdminSecretkey(), jwtProperties.getAdminTtl(),claims);
        return adminToken;
    }
/**
     * 管理员注册
     * @param adminRegisterDTO
     */
    @Override
    public void adminRegister(AdminRegisterDTO adminRegisterDTO) {
        if( adminRegisterDTO.getPermission() == null ){
            throw new RuntimeException("权限不能为空");
        }
         String username = userMapper.getUsernameByUsername(adminRegisterDTO.getUsername());
        if(username != null){
            throw new RuntimeException("用户名已存在");
        }
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("1")){
            throw new RuntimeException("你没有权限注册管理员");
        }
        user.setUsername(adminRegisterDTO.getUsername());
        user.setPassword(adminRegisterDTO.getPassword());
        user.setPermission(adminRegisterDTO.getPermission());
        user.setRegisteredTime(LocalDateTime.now());
        userMapper.adminInsert(user);
    }
}
