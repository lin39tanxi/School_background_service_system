package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.vo.NotificationVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface NotificationService {
    /**
     * 添加通知
     */
    void addNotification(String title, String content, MultipartFile[] imageUrlsArray);
/**
 * 获取通知列表
 */
    PageResult getNotificationList(String orderKey,Integer pageNum, Integer pageSize, LocalDate beginTime,LocalDate  endTime);
    /**
     * 获取通知详情
     */
    NotificationVO getNotificationDetail(Integer notificationId);
/**
 * 删除通知
 */
    void deleteNotification(Integer notificationId);
}
