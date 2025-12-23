package com.we_are_team.school_background_service_system.controller.user;

import com.we_are_team.school_background_service_system.pojo.vo.FeedbackVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.FeedbacksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping("/user/feedbacks")
public class FeedbacksController {
    @Autowired
    private FeedbacksService feedbacksService;
   @PostMapping
    public Result submitFeedback(@RequestParam String comment, @RequestParam(defaultValue = "0") Integer isAnonymous,@RequestParam(required = false) MultipartFile[] imageUrlsArray){
       feedbacksService.submitFeedback(comment, isAnonymous, imageUrlsArray);
       return Result.success("提交成功");
   }

   /**
    * 获取反馈列表
    * @param pageNum
    * @param pageSize
    * @param orderKey
    * @param beginTime
    * @param endTime
    * @return
    */
   @GetMapping("/my")
    public Result<PageResult> getMyFeedbacks(@RequestParam(defaultValue = "1") Integer pageNum,
                                                         @RequestParam(defaultValue = "1000") Integer pageSize,
                                                         @RequestParam(defaultValue = "") String orderKey,
                                                         @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime,
                                                         @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endTime)
   {
        PageResult pageResult =  feedbacksService.getMyFeedbacks(pageNum,pageSize,orderKey,beginTime,endTime);
         return Result.success("获取我的反馈列表成功",pageResult);}
/**
    * 获取反馈详情
    * @param feedbackId
    * @return
    */
    @GetMapping("/{feedbackId}")
    public Result<FeedbackVO> getFeedbackDetail(@PathVariable Integer feedbackId){
        return Result.success("获取反馈详情成功",feedbacksService.getFeedbackDetail(feedbackId));
    }

    /**
    * 删除反馈
    * @param feedbackId
    * @return
    */
    @DeleteMapping("/{feedbackId}")
    public Result deleteFeedback(@PathVariable Integer feedbackId){
        feedbacksService.deleteFeedback(feedbackId);
        return Result.success("删除成功");
    }

}
