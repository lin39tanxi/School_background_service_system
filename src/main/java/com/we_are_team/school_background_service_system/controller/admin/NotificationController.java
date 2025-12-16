package com.we_are_team.school_background_service_system.controller.admin;

import com.we_are_team.school_background_service_system.pojo.vo.NotificationVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Slf4j
@RequestMapping("/admin/notification")
@RestController("adminNotificationController")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    /**
     * 添加通知
     */
    @PostMapping
    public Result addNotification(@RequestParam String title, @RequestParam String content, @RequestParam(required = false) MultipartFile[] imageUrlsArray) {
        log.info("添加通知,{}", title);
        notificationService.addNotification(title, content, imageUrlsArray);
        return Result.success("添加成功");
    }

    /**
     * 获取通知列表
     */
    @GetMapping
    public Result<PageResult> getNotificationList(@RequestParam(required = false) String orderKey,
                                                  @RequestParam(required = false ,defaultValue ="1") Integer pageNum,
                                                  @RequestParam(required = false, defaultValue ="10") Integer pageSize,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime) {
        PageResult pageResult = notificationService.getNotificationList(orderKey, pageNum, pageSize, beginTime, endTime);
        return Result.success("获取成功", pageResult);
                                                 }
     @GetMapping("/{notificationId}")
     public Result<NotificationVO> getNotificationDetail(@PathVariable Integer notificationId){
         log.info("获取通知详情,{}", notificationId);
         NotificationVO notificationVO = notificationService.getNotificationDetail(notificationId);
         return Result.success("获取详细成功", notificationVO);
     }

     /**
      * 删除通知
      */
      @DeleteMapping("/{notificationId}")
      public Result deleteNotification(@PathVariable Integer notificationId){
          log.info("删除通知,{}", notificationId);
          notificationService.deleteNotification(notificationId);
          return Result.success("删除成功");
      }
}
