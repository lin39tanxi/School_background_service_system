package com.we_are_team.school_background_service_system.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.we_are_team.school_background_service_system.context.BaseContext;
import com.we_are_team.school_background_service_system.mapper.FeedbacksMapper;
import com.we_are_team.school_background_service_system.mapper.UserMapper;
import com.we_are_team.school_background_service_system.pojo.entity.Feedback;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import com.we_are_team.school_background_service_system.pojo.vo.FeedbackVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetFeedbackListVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.service.FeedbacksService;
import com.we_are_team.school_background_service_system.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Slf4j
public class FeedbacksServiceImpl implements FeedbacksService {

    @Autowired
    private FeedbacksMapper feedbacksMapper;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private UserMapper usersMapper;
/**
 * 用户端提交反馈
 */

    @Override
    public void submitFeedback(String comment, Integer isAnonymous, MultipartFile[] imageUrlsArray) {
        Feedback feedback = new Feedback();
        User user = usersMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("0")){
            throw new RuntimeException("这是用户端提交反馈");
        }

        feedback.setUserId(BaseContext.getCurrentId());
        feedback.setContent(comment);
        feedback.setIsAnonymous(isAnonymous);
        feedback.setCreatedTime(LocalDateTime.now());
        List<String> urlsArray = new ArrayList<>();
        if(imageUrlsArray == null || imageUrlsArray.length == 0){

            feedbacksMapper.insert(feedback);
            return;
        }
        else if ( imageUrlsArray.length> 0  && !imageUrlsArray[0].getOriginalFilename().equals("")) {
            log.info("图片数组{}",imageUrlsArray[0].getOriginalFilename());
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
            }
            String result = String.join(",", urlsArray);
            log.info("图片字符串{}",result);
            feedback.setImageUrls(result);

        }
            feedbacksMapper.insert(feedback);

//        if( !urlsArray.isEmpty()){
//            String result = String.join(",", urlsArray);
//            log.info("图片字符串{}",result);
//            feedback.setImageUrls(result);
//            feedbacksMapper.insert(feedback);
//        }

    }

    /**
     * 获取我的反馈
     * @param pageNum
     * @param pageSize
     * @param orderKey
     * @param beginTime
     * @param endTime
     * @return
     */

    @Override
    public PageResult getMyFeedbacks(Integer pageNum, Integer pageSize, String orderKey, LocalDate beginTime, LocalDate endTime) {
        User user = usersMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("0")){
            throw new RuntimeException("这是用户端获取自己的反馈列表");
        }
        PageHelper.startPage(pageNum, pageSize);
        Integer userId = BaseContext.getCurrentId();
        Page<GetFeedbackListVO> feedbacks = feedbacksMapper.getList(userId, orderKey, beginTime, endTime);
        feedbacks.getResult().forEach(feedback->{
            if (feedback.getImageUrls() != null && !feedback.getImageUrls().equals("")) {
                String[] imageUrls = feedback.getImageUrls().split(",");
                feedback.setImageUrls(imageUrls[0]);
            }

        });
        return new PageResult(feedbacks.getTotal(),feedbacks.getResult());
    }

    @Override
    public FeedbackVO getFeedbackDetail(Integer feedbackId) {
        User user = usersMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("0")){
            throw new RuntimeException("这是用户端获取反馈详情");
        }
        Feedback feedback = feedbacksMapper.getFeedbackByFeedbackId(feedbackId);
        if(feedback == null){
            throw new RuntimeException("反馈不存在");
        }
        if(feedback.getUserId() == null || !feedback.getUserId().equals(BaseContext.getCurrentId())){
            throw new RuntimeException("你没有这个权限查询这个反馈");
        }
        if(feedback.getImageUrls() == null || feedback.getImageUrls().equals("")){
            FeedbackVO feedbackVO = FeedbackVO.builder()
                    .feedbackId(feedback.getFeedbackId())
                    .content(feedback.getContent())
                    .isAnonymous(feedback.getIsAnonymous())
                    .createdTime(feedback.getCreatedTime())
                    .studentNumber(feedback.getStudentNumber())
                    .build();
            return feedbackVO;
        }else {
            String[] urlArray = feedback.getImageUrls().split(",");
            List<String> urls = new ArrayList<>(Arrays.asList(urlArray));
            FeedbackVO feedbackVO = FeedbackVO.builder()
                    .feedbackId(feedback.getFeedbackId())
                    .content(feedback.getContent())
                    .isAnonymous(feedback.getIsAnonymous())
                    .createdTime(feedback.getCreatedTime())
                    .imageUrls(urls)
                    .studentNumber(feedback.getStudentNumber())
                    .build();
            return feedbackVO;
        }



    }

    @Override
    public void deleteFeedback(Integer feedbackId) {
        User user = usersMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("0")){
            throw new RuntimeException("这是用户端删除反馈");
        }
        Feedback feedback = feedbacksMapper.getFeedbackByFeedbackId(feedbackId);
        if(feedback == null){
            throw new RuntimeException("反馈不存在");
        }
        if(feedback.getUserId() == null || !feedback.getUserId().equals(BaseContext.getCurrentId())){
            throw new RuntimeException("你没有这个权限删除这个反馈");
        }
        feedbacksMapper.deleteFeedback(feedbackId);
    }

    @Override
    public PageResult adminGetMyFeedbacks(Integer pageNum, Integer pageSize, String orderKey, LocalDate beginTime, LocalDate endTime) {
       User user = usersMapper.getUserByUserId(BaseContext.getCurrentId());
       if(!user.getPermission().contains("3")){
           throw new RuntimeException("你没有权限获取意见列表");
       }
        PageHelper.startPage(pageNum, pageSize);
        Integer userId = null;
        Page<GetFeedbackListVO> feedbacks = feedbacksMapper.getList(userId, orderKey, beginTime, endTime);
        feedbacks.getResult().forEach(feedback->{
             if(feedback.getIsAnonymous() == 0){
                 feedback.setStudentNumber("匿名用户");
             }
//            获取第一张图片
            if (feedback.getImageUrls() != null && !feedback.getImageUrls().equals("")) {
                String[] imageUrls = feedback.getImageUrls().split(",");
                feedback.setImageUrls(imageUrls[0]);
            }

        });
        return new PageResult(feedbacks.getTotal(),feedbacks.getResult());
    }
/**
 * 管理员获取反馈详情
 * @param feedbackId
 * @return
 */
    @Override
    public FeedbackVO adminGetFeedbackDetail(Integer feedbackId) {
        User user = usersMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("3")){
            throw new RuntimeException("你没有权限获取反馈详情");
        }
        Feedback feedback = feedbacksMapper.getFeedbackByFeedbackId(feedbackId);
        if(feedback == null){
            throw new RuntimeException("反馈不存在");
        }
        FeedbackVO feedbackVO = new FeedbackVO();
        if(feedback.getIsAnonymous() == 0){
            feedback.setStudentNumber("匿名用户");
//
        }
        if(feedback.getImageUrls() == null || feedback.getImageUrls().equals("")){
             feedbackVO = FeedbackVO.builder()
                    .feedbackId(feedback.getFeedbackId())
                    .content(feedback.getContent())
                    .isAnonymous(feedback.getIsAnonymous())
                    .createdTime(feedback.getCreatedTime())
                    .studentNumber(feedback.getStudentNumber())
                    .build();

        }else {
            String[] urlArray = feedback.getImageUrls().split(",");
            List<String> urls = new ArrayList<>(Arrays.asList(urlArray));
             feedbackVO = FeedbackVO.builder()
                    .feedbackId(feedback.getFeedbackId())
                    .content(feedback.getContent())
                    .isAnonymous(feedback.getIsAnonymous())
                    .createdTime(feedback.getCreatedTime())
                    .imageUrls(urls)
                    .studentNumber(feedback.getStudentNumber())
                    .build();

    }

        return feedbackVO;
}}


