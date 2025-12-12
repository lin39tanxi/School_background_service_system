package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.dto.RepairEvaluationCreateDTO;
import com.we_are_team.school_background_service_system.pojo.vo.RepairOrderVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface ReparisService {
    /**
     * 提交报修单
     * @param description
     * @param imageUrlsArray
     */
    void submitRepair(String description, MultipartFile[] imageUrlsArray);

    /**
     * 获取我的报修列表
     * @return
     */
    PageResult getMyRepairs(String status , Integer pageNum, Integer pageSize, String orderKey,LocalDate beginTime,LocalDate endTime);
/**
 * 获取报修单详情
 * @param orderId
 * @return
 */
    RepairOrderVO getRepairOrdeDetail(Integer orderId);

    /**
     * 取消报修单
     * @param orderId
     */
    void cancelRepair(Integer orderId);

    /**
     * 评价订单
     * @param orderId
     * @param repairEvaluationCreateDTO
     */
    void commentRepair(Integer orderId, RepairEvaluationCreateDTO repairEvaluationCreateDTO);

    /**
     * 删除评价
     * @param orderId
     */
    void deleteComment(Integer orderId);
}
