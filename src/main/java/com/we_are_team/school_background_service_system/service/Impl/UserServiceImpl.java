package com.we_are_team.school_background_service_system.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.we_are_team.school_background_service_system.context.BaseContext;
import com.we_are_team.school_background_service_system.mapper.DormitoryMapper;
import com.we_are_team.school_background_service_system.mapper.FeedbacksMapper;
import com.we_are_team.school_background_service_system.mapper.RepairsMapper;
import com.we_are_team.school_background_service_system.mapper.UserMapper;
import com.we_are_team.school_background_service_system.pojo.dto.*;
import com.we_are_team.school_background_service_system.pojo.entity.ApplicationForm;
import com.we_are_team.school_background_service_system.pojo.entity.RepairOrder;
import com.we_are_team.school_background_service_system.pojo.entity.Student;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import com.we_are_team.school_background_service_system.pojo.vo.UserLoginVO;
import com.we_are_team.school_background_service_system.pojo.vo.UserVO;
import com.we_are_team.school_background_service_system.properties.JwtProperties;

import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.service.DormitoryService;
import com.we_are_team.school_background_service_system.service.FeedbacksService;
import com.we_are_team.school_background_service_system.service.ReparisService;
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
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private DormitoryMapper dormitoryMapper;
    @Autowired
    private RepairsMapper repairsMapper;
    @Autowired
    private FeedbacksMapper feedbacksMapper;

    /**
     * 用户注册
     * 
     * @param userRegisterDTO
     */

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO.getStudentNumber() == null || userRegisterDTO.getStudentNumber().equals("")
                || userRegisterDTO.getName() == null || userRegisterDTO.getName().equals("")
                || userRegisterDTO.getPassword() == null || userRegisterDTO.getPassword().equals("")
                || userRegisterDTO.getNickname() == null || userRegisterDTO.getNickname().equals("")
                || userRegisterDTO.getUsername() == null || userRegisterDTO.getUsername().equals("")) {
            throw new RuntimeException("请填写完整信息");
        }
        if (userRegisterDTO.getUsername().length() < 6 || userRegisterDTO.getUsername().length() > 20) {
            throw new RuntimeException("账号长度不能小于6位或者大于20位");
        }
        if (userRegisterDTO.getPassword().length() < 6 || userRegisterDTO.getPassword().length() > 20) {
            throw new RuntimeException("密码长度不能小于6位或者大于20位");
        }
        String name = userRegisterDTO.getName();
        String studentNumber = userRegisterDTO.getStudentNumber();
        String username = userRegisterDTO.getUsername();
        if (userMapper.getUsernameByUsername(username) != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 查找学校里面是否有这学生
        Student student = userMapper.getStudentByNameAndStudentNumber(name, studentNumber);
        if (student == null || student.equals("")) {
            throw new RuntimeException("学生学号或姓名错误");
        }

        // 查找user表是否已经注册这个学号
        User user = userMapper.getUserByStudentNumber(userRegisterDTO.getStudentNumber());
        if (user != null) {
            throw new RuntimeException("该学号已经注册过了");
        }
        userMapper.insert(userRegisterDTO);

    }

    /**
     * 用户登录
     * 
     * @param userLoginDTO
     * @return UserLoginVO
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        User user = new User();

        user.setStudentNumber(userLoginDTO.getStudentNumber());
        user.setPassword(userLoginDTO.getPassword());
        user.setUsername(userLoginDTO.getUsername());
        UserLoginVO userLoginVO = new UserLoginVO();
        user = userMapper.getUserByPasswordAndStudentNumberOrUsername(user);
        log.info("用户登录信息：{}", user);
        if (user == null || user.equals("")) {
            throw new RuntimeException("账号或密码错误");
        }
        if (!user.getPermission().contains("0")) {
            throw new RuntimeException("该用户没有权限用户登录");
        }
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getUserId());
        log.info("claims:{}", claims);
        String token = JwtUtil.createJwt(jwtProperties.getUserSecretkey(), jwtProperties.getUserTtl(), claims);
        userLoginVO.setToken(token);
        return userLoginVO;
    }

    /**
     * 获取用户信息
     * 
     * @return UserVO
     */
    @Override
    public UserVO getUserInfo() {
        int userId = BaseContext.getCurrentId();
        User user = userMapper.getUserByUserId(userId);
        log.info("用户信息：{}", user);
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
        // userVO.setCreatedTime(user.getRegisteredTime());
        userVO.setUpdatedTime(user.getUpdatedTime());
        userVO.setRegisteredTime(user.getRegisteredTime());
        userVO.setPhone(student.getPhone());
        return userVO;
    }

    /**
     * 修改用户昵称
     * 
     * @param nickname
     */

    @Override
    public void updateUserNickname(String nickname) {
        Integer id = BaseContext.getCurrentId();
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new RuntimeException("用户昵称不能为空");
        }
        if (nickname.length() > 20) {
            throw new RuntimeException("昵称长度不能超过20个字符");
        }
        userMapper.updateNicknameById(id, nickname.trim());

    }

    /**
     * 修改用户密码
     * 
     * @param changePasswordDTO
     */
    @Override
    public void updatePassword(ChangePasswordDTO changePasswordDTO) {
        // 查找密码对比数据
        Integer userId = BaseContext.getCurrentId();
        User user = userMapper.getUserByUserId(userId);
        if (!user.getPassword().equals(changePasswordDTO.getOldPassword())) {
            log.info("原来密码：{}", user.getPassword());
            log.info("输入的旧密码：{}", changePasswordDTO.getOldPassword());
            throw new RuntimeException("旧密码不正确");
        }
        if (changePasswordDTO.getNewPassword().equals("")) {
            throw new RuntimeException("新密码不能为空");
        }
        if (changePasswordDTO.getNewPassword().length() < 6 || changePasswordDTO.getNewPassword().length() > 20) {
            throw new RuntimeException("密码长度不能小于6位或者大于20位");
        }
        userMapper.updatePassword(userId, changePasswordDTO);

    }

    @Override
    public String uploadAvatar(MultipartFile avatarFile) {
        // log.info("上传头像{}", avatarFile);
        /**
         * 上传头像
         */

        if (avatarFile == null || avatarFile.getOriginalFilename().equals("")) {
            throw new RuntimeException("请选择文件");
        }
        String originalFilename = avatarFile.getOriginalFilename();
        String objectName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String obj = UUID.randomUUID().toString() + objectName;
        String url = null;
        try {
            url = aliOssUtil.upload(avatarFile.getBytes(), obj);
            userMapper.updateAvatarUrlById(BaseContext.getCurrentId(), url);
            return url;
        } catch (IOException e) {
            throw new RuntimeException("上传失败");
        }
    }

    /**
     * 管理员登录
     * 
     * @param adminLoginDTO
     * @return
     */
    @Override
    public String adminLogin(AdminLoginDTO adminLoginDTO) {
        User user = new User();
        user.setUsername(adminLoginDTO.getUsername());
        user.setPassword(adminLoginDTO.getPassword());
        user = userMapper.getUserByPasswordAndStudentNumberOrUsername(user);
        log.info("用户登录信息：{}", user);
        // 没有这个用户
        if (user == null || user.equals("")) {
            throw new RuntimeException("账号或密码错误");
        }
        if (StringUtils.equals(user.getPermission(), "0")) {
            throw new RuntimeException("你没有权限登录管理端");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        log.info("claims:{}", claims);
        String adminToken = JwtUtil.createJwt(jwtProperties.getAdminSecretkey(), jwtProperties.getAdminTtl(), claims);
        return adminToken;
    }

    /**
     * 管理员注册
     * 
     * @param adminRegisterDTO
     */
    @Override
    public void adminRegister(AdminRegisterDTO adminRegisterDTO) {
        if (adminRegisterDTO.getUsername() == null || adminRegisterDTO.getUsername().equals("")) {
            throw new RuntimeException("用户名不能为空");
        }
        if (adminRegisterDTO.getPassword() == null || adminRegisterDTO.getPassword().equals("")) {
            throw new RuntimeException("密码不能为空");
        }
        // if(adminRegisterDTO.getNickname() == null ||
        // adminRegisterDTO.getNickname().equals("")){
        // throw new RuntimeException("昵称不能为空");
        // }
        if (adminRegisterDTO.getUsername().length() < 6 || adminRegisterDTO.getUsername().length() > 20) {
            throw new RuntimeException("用户名长度不能小于6位或者大于20位");
        }
        if (adminRegisterDTO.getPassword().length() < 6 || adminRegisterDTO.getPassword().length() > 20) {
            throw new RuntimeException("密码长度不能小于6位或者大于20位");
        }
        if (adminRegisterDTO.getPermission() == null) {
            throw new RuntimeException("权限不能为空");
        }
        String username = userMapper.getUsernameByUsername(adminRegisterDTO.getUsername());
        if (username != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if (!user.getPermission().contains("1")) {
            throw new RuntimeException("你没有权限注册管理员");
        }
        user.setUsername(adminRegisterDTO.getUsername());
        user.setPassword(adminRegisterDTO.getPassword());
        user.setPermission(adminRegisterDTO.getPermission());
        user.setRegisteredTime(LocalDateTime.now());
        userMapper.adminInsert(user);
    }

    @Override
    public void updateAdminPassword(UpdatePasswordNoOldDTO updatePasswordNoOldDTO) {
        if (updatePasswordNoOldDTO.getNewPassword().equals("")) {
            throw new RuntimeException("新密码不能为空");
        }
        if (updatePasswordNoOldDTO.getNewPassword().length() < 6
                || updatePasswordNoOldDTO.getNewPassword().length() > 20) {
            throw new RuntimeException("密码长度不能小于6位或者大于20位");
        }
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if (!user.getPermission().contains("1")) {
            throw new RuntimeException("你不是超级管理员，无权限无密码更新");
        }
        userMapper.updateAdminPassword(updatePasswordNoOldDTO);

    }

    @Override
    public void updatePermission(UpdatePermissionDTO updatePermissionDTO) {
        if (updatePermissionDTO.getNewPermission().isEmpty()) {
            throw new RuntimeException("新权限不能为空");
        }
        if (updatePermissionDTO.getNewPermission().contains("1")) {
            throw new RuntimeException("新权限不能是超级管理员");
        }
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if (!user.getPermission().contains("1")) {
            throw new RuntimeException("你不是超级管理员，无法更新权限");
        }
        userMapper.updateAdminPermission(updatePermissionDTO);
    }

    @Override
    public void deleteUserByAdminId(Integer userId) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if (user.getUserId().equals(userId)) {
            throw new RuntimeException("不能删除自己");
        }
        if (user.getUsername().equals("admin")) {
            throw new RuntimeException("不能删除初始超级管理员");
        }
        if (!user.getPermission().contains("1")) {
            throw new RuntimeException("你没有权限删除管理员");
        }
        userMapper.deleteUserByAdminId(userId);
    }

    /**
     * 获取所有管理员
     * 
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult getAllAdmin(Integer pageNum, Integer pageSize) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum, pageSize);
        Page<UserVO> adminVO = userMapper.getAllAdmin();
        return new PageResult(adminVO.getTotal(), adminVO.getResult());
    }

    @Override
    public User adminGetUserInfo() {
        Integer userId = BaseContext.getCurrentId();
        return userMapper.getUserByUserId(userId);
    }

    @Override
    public List<ApplicationForm> getApplicationForm() {
        // @Autowired
        // private DormitoryService dormitoryService;
        // @Autowired
        // private ReparisService reparisService;
        // @Autowired
        // private FeedbacksService feedbacksService;
        Integer userId = BaseContext.getCurrentId();
        // 保修单
        List<ApplicationForm> repairsApplicationForms = repairsMapper.getAppcationFormByUserId(userId);
        for (ApplicationForm repairsApplicationForm : repairsApplicationForms) {
            repairsApplicationForm.setTitle("维修预约");
            repairsApplicationForm.setType(0);
            if (repairsApplicationForm.getUpdatedTime() != null) {
                repairsApplicationForm.setCreatedTime(repairsApplicationForm.getUpdatedTime());
            }
        }
        // 意见反馈
        List<ApplicationForm> feedbacksApplicationForms = feedbacksMapper.getAppcationFormByUserId(userId);

        for (ApplicationForm feedbacksApplicationForm : feedbacksApplicationForms) {
            feedbacksApplicationForm.setTitle("意见反馈");
            feedbacksApplicationForm.setType(1);
            feedbacksApplicationForm.setStatus(0);
        }
        log.info("反馈单,{}", feedbacksApplicationForms);

        // 申请表
        List<ApplicationForm> dormitoryApplicationForms = dormitoryMapper.getAppcationFormByUserId(userId);
        for (ApplicationForm dormitoryApplicationForm : dormitoryApplicationForms) {
            dormitoryApplicationForm.setTitle("宿舍调换");
            dormitoryApplicationForm.setType(2);
            for (ApplicationForm applicationForm : dormitoryApplicationForms) {
                if (applicationForm.getUpdatedTime() != null) {
                    applicationForm.setCreatedTime(applicationForm.getUpdatedTime());
                }
            }
        }
        log.info("申请表,{}", dormitoryApplicationForms);
        // 创建合并后的列表
        List<ApplicationForm> mergedList = new ArrayList<>();

        // 添加所有元素
        mergedList.addAll(repairsApplicationForms);
        mergedList.addAll(feedbacksApplicationForms);
        mergedList.addAll(dormitoryApplicationForms);

        // 排序
        mergedList.sort(
                Comparator.comparing(ApplicationForm::getCreatedTime, Comparator.nullsLast(Comparator.reverseOrder())));

        return mergedList;
    }

    @Override
    public void changeMyPhone(String newPhone) {
        if (newPhone == null || newPhone.trim().isEmpty()) {
            throw new RuntimeException("手机号不能为空");
        }
        if (!newPhone.matches("^1[3-9]\\d{9}$")) {
            throw new RuntimeException("手机号格式不正确");
        }
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        String studentNumber = user.getStudentNumber();
        userMapper.changeMyPhoneByStudentNumber(newPhone.trim(), studentNumber);

    }

}
