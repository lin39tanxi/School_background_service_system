package com.we_are_team.school_background_service_system.controller.admin;

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

@RestController("adminFeedbacksController")
@Slf4j
@RequestMapping("/admin/feedbacks")
public class FeedbacksController {
    @Autowired
    private FeedbacksService feedbacksService;
    @GetMapping("/list")
    public Result<PageResult> getMyFeedbacks(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(defaultValue = "") String orderKey,
                                             @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime,
                                             @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endTime)
    {
        PageResult pageResult =  feedbacksService.adminGetMyFeedbacks(pageNum,pageSize,orderKey,beginTime,endTime);
        return Result.success("获取我的反馈列表成功",pageResult);}


     @GetMapping("/{feedbackId}")
    public Result<FeedbackVO> getFeedbackDetail(@PathVariable Integer feedbackId){
        return Result.success("获取反馈详情成功",feedbacksService.adminGetFeedbackDetail(feedbackId));
     }
}
