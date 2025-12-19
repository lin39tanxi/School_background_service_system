package com.we_are_team.school_background_service_system.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.we_are_team.school_background_service_system.context.BaseContext;
import com.we_are_team.school_background_service_system.mapper.NotificationMapper;
import com.we_are_team.school_background_service_system.mapper.UserMapper;
import com.we_are_team.school_background_service_system.pojo.entity.Notification;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import com.we_are_team.school_background_service_system.pojo.vo.GetLostAndFoundVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetNotificationListVO;
import com.we_are_team.school_background_service_system.pojo.vo.NotificationVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.service.NotificationService;
import com.we_are_team.school_background_service_system.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void addNotification(String title, String content, MultipartFile[] imageUrlsArray) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().equals("4")){
            throw new RuntimeException("你没有权限提交通知");
        }
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setContent(content);
        notification.setAdminId(BaseContext.getCurrentId());
        notification.setPublishTime(LocalDateTime.now());
        List<String> urlsArray = new ArrayList<>();
        if(imageUrlsArray == null || imageUrlsArray.length == 0){
            notificationMapper.insert(notification);
            return;
        }
        if( imageUrlsArray.length > 0 && !imageUrlsArray[0].getOriginalFilename().equals("")){
            for (MultipartFile file : imageUrlsArray) {
                String originalFilename = file.getOriginalFilename();
                String objectName = originalFilename.substring(originalFilename.lastIndexOf("."));
                String obj = UUID.randomUUID().toString() + objectName;
                String url = null;
                try {
                    url = aliOssUtil.upload(file.getBytes(), obj);
                    urlsArray.add(url);
                } catch (IOException e) {
                    throw new RuntimeException("上传失败");
                }
            }  String result = String.join(",", urlsArray);
            log.info("图片字符串{}",result);
            notification.setImageUrls(result);
        }
        notificationMapper.insert(notification);

    }
/**
 * 获取通知列表
 */
    @Override
    public PageResult getNotificationList(String orderKey,Integer pageNum, Integer pageSize, LocalDate beginTime, LocalDate endTime) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("0") || !user.getPermission().contains("4")){
            throw new RuntimeException("你没有权限查看通知列表");
        }
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum, pageSize);
        Page<GetNotificationListVO> lostAndFoundVOList =    notificationMapper.getNotificationList(orderKey,beginTime, endTime);
        return new PageResult(lostAndFoundVOList.getTotal(), lostAndFoundVOList);
    }
/**
 * 获取通知详情
 */
    @Override
    public NotificationVO getNotificationDetail(Integer notificationId) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("0") || !user.getPermission().contains("4")){
            throw new RuntimeException("你没有权限查看通知详情");
        }
        Notification notification = notificationMapper.getNotificationDetail(notificationId);
        NotificationVO notificationVO = new NotificationVO();
        BeanUtils.copyProperties(notification, notificationVO);
        String[] imageUrls = null;

        if(notification.getImageUrls() != null && !notification.getImageUrls().equals("")){
            imageUrls = notification.getImageUrls().split(",");
            notificationVO.setImageUrls(Arrays.asList(imageUrls));
        }

        return notificationVO;
    }

    @Override
    public void deleteNotification(Integer notificationId) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("4")){
            throw new RuntimeException("你没有权限删除通知");
        }
        notificationMapper.deleteNotification(notificationId);
    }
}
