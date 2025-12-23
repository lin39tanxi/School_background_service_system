package com.we_are_team.school_background_service_system.controller.user;
import com.we_are_team.school_background_service_system.pojo.dto.RepairEvaluationCreateDTO;
import com.we_are_team.school_background_service_system.pojo.vo.RepairOrderVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.ReparisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jacoco.agent.rt.internal_43f5073.core.internal.flow.IFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/user")
public class RepairsController {
    @Autowired
    private ReparisService reparisService;
    /**
     * 提交保修申请
     */
    @PostMapping("/repairs")
    public Result submitRepair(@RequestParam String description,
                               @RequestParam String address,
                               @RequestParam(required = false) MultipartFile[] imageUrlsArray,
                               @RequestParam String phone ,
                               @RequestParam("appointmentBegin")
                                   @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime appointmentBegin,
                               @RequestParam("appointmentEnd")
                                   @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")LocalDateTime appointmentEnd) {
        if(imageUrlsArray != null) {
            log.info("上传的图片数量为:{}", imageUrlsArray.length);
        }
        log.info("提交预约时间,{}{}",appointmentBegin.toString(),appointmentEnd.toString());

        reparisService.submitRepair(description, address,imageUrlsArray,phone,appointmentBegin,appointmentEnd);
        return Result.success("报修提交成功");
    }

    /**
     * 获取我的报修列表
     */

    @GetMapping("/repairs/my")
    public Result<PageResult> getMyRepairs( String  status ,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "1000") Integer pageSize, String orderKey,
                                            @RequestParam(defaultValue = "")
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime,
                                            @RequestParam(defaultValue = "")
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime) {
        PageResult pageResult = reparisService.getMyRepairs(status, pageNum, pageSize, orderKey, beginTime, endTime);
        return Result.success("获取成功", pageResult);
    }
    /**
     * 获取报修单详情
     */
    @GetMapping("/repairs/{orderId}")
    public Result<RepairOrderVO> getRepairOrdeDetail(@PathVariable Integer orderId){
        return Result.success("获得成功",reparisService.getRepairOrdeDetail(orderId));
    }

    /**
     * 取消报修
     */
    @PutMapping("/repairs/{orderId}/cancel")
    public Result cancelRepair(@PathVariable Integer orderId){
        reparisService.cancelRepair(orderId);
        return Result.success("取消报修成功");
    }

    /**
     * 评价报修单
     */
    @PutMapping("/repairs/{orderId}/comment")
    public Result commentRepair(@PathVariable Integer orderId, @RequestBody RepairEvaluationCreateDTO repairEvaluationCreateDTO){
        reparisService.commentRepair(orderId, repairEvaluationCreateDTO);
        return Result.success("评价成功");
    }

    /**
     * 删除保修单评价
     */
    @PutMapping("/repairs/{orderId}/deleteComment")
    public Result deleteComment(@PathVariable Integer orderId){
        reparisService.deleteComment(orderId);
        return Result.success("删除评价成功");
    }
}
