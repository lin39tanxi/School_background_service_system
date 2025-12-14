package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.vo.FeedbackVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetFeedbackVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface FeedbacksService {
    /**
     * 提交反馈
     */
    void submitFeedback(String comment, Integer isAnonymous, MultipartFile[] imageUrlsArray);




    /**
     * 获取我的反馈
     */
    PageResult getMyFeedbacks(Integer pageNum, Integer pageSize, String orderKey, LocalDate beginTime, LocalDate endTime);

    /**
     * 获取单条反馈详情信息
     * @param feedbackId
     * @return
     */
    FeedbackVO getFeedbackDetail(Integer feedbackId);
/**
 * 删除反馈
 */
    void deleteFeedback(Integer feedbackId);
/**
 * 获取管理员我的反馈
 */
    PageResult adminGetMyFeedbacks(Integer pageNum, Integer pageSize, String orderKey, LocalDate beginTime, LocalDate endTime);
/**
 * 获取管理员反馈详情
 */
    FeedbackVO adminGetFeedbackDetail(Integer feedbackId);
}
