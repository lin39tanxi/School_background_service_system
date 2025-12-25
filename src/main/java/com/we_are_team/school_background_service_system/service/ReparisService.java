package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.dto.RepairEvaluationCreateDTO;
import com.we_are_team.school_background_service_system.pojo.entity.ApplicationForm;
import com.we_are_team.school_background_service_system.pojo.vo.RepairOrderVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReparisService {
    /**
     * 提交报修单
     * @param description
     * @param imageUrlsArray
     */
    void submitRepair(String description, String address, MultipartFile[] imageUrlsArray, String phone, LocalDateTime appointmentBegin, LocalDateTime appointmentEnd);

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
/**
 * 管理员获取报修单
 * @return
 */
    PageResult AdminGetMyRepairs(String status, Integer pageNum, Integer pageSize, String orderKey, LocalDate beginTime, LocalDate endTime);

    /**
     * 管理端获得保修订单详情
     * @param orderId
     * @return
     */
    RepairOrderVO AdmingetRepairOrdeDetail(Integer orderId);
/**
 * 修改报修单状态
 * @param orderId
 */
    void updateRepairStatus(Integer orderId);
    /**
     * 拒绝报修单
     * @param orderId
     * @param rejectReason
     */
    void regiectRepair(Integer orderId, String rejectReason);
/**
 * 完成报修单
 * @param orderId
 */
    void completeRepair(Integer orderId);

    /**
     * 删除通知
     * @param orderId
     */
    void deleteRepair(Integer orderId);
}
