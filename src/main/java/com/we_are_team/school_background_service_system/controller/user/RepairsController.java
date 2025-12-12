package com.we_are_team.school_background_service_system.controller.user;
import com.we_are_team.school_background_service_system.pojo.vo.RepairOrderVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.ReparisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
    public Result submitRepair(@RequestParam String description, @RequestParam(required = false) MultipartFile[] imageUrlsArray ) {

        reparisService.submitRepair(description, imageUrlsArray);
        return Result.success("报修提交成功");
    }

    /**
     * 获取我的报修列表
     */
    @GetMapping("/repairs/my")
    public Result<PageResult> getMyRepairs( String  status , @RequestParam("pageNum") Integer pageNum, Integer pageSize, String orderKey, LocalDate beginTime, LocalDate endTime) {
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
}
