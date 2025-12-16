package com.we_are_team.school_background_service_system.mapper;

import com.github.pagehelper.Page;
import com.we_are_team.school_background_service_system.pojo.entity.Notification;
import com.we_are_team.school_background_service_system.pojo.vo.GetNotificationListVO;
import com.we_are_team.school_background_service_system.pojo.vo.NotificationVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;

@Mapper
public interface NotificationMapper {
    /**
     * 添加通知
     */
    @Insert("insert into notification(title,content,image_urls,admin_id,publish_time) " +
            "values(#{notification.title},#{notification.content},#{notification.imageUrls}," +
            "#{notification.adminId},#{notification.publishTime})")
    void insert(@Param("notification") Notification notification);

    Page<GetNotificationListVO> getNotificationList(@Param("orderKey") String orderKey,@Param("beginTime") LocalDate beginTime,@Param("endTime") LocalDate endTime);

    @Select("select * from notification where notification_id=#{notificationId}")
    Notification getNotificationDetail(@Param("notificationId") Integer notificationId);

    /**
     * 删除通知
     */
    @Delete("delete from notification where notification_id=#{notificationId}")
    void deleteNotification(Integer notificationId);
}
