package com.we_are_team.school_background_service_system.controller.admin;
import com.we_are_team.school_background_service_system.pojo.dto.RepairEvaluationCreateDTO;
import com.we_are_team.school_background_service_system.pojo.vo.RepairOrderVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.ReparisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Slf4j
@RestController("adminRepairsController")
@RequestMapping("/admin")
public class RepairsController {
    @Autowired
    private ReparisService reparisService;


    /**
     * 获取我的报修列表
     */
    @GetMapping("/repairs")
    public Result<PageResult> getMyRepairs(String  status ,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize, String orderKey, @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime, @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime) {
        PageResult pageResult = reparisService.AdminGetMyRepairs(status, pageNum, pageSize, orderKey, beginTime, endTime);
        return Result.success("获取报单列表成功", pageResult);
    }
    /**
     * 获取报修单详情
     */
    @GetMapping("/repairs/{orderId}")
    public Result<RepairOrderVO> getRepairOrdeDetail(@PathVariable Integer orderId){
        return Result.success("获得成功",reparisService.AdmingetRepairOrdeDetail(orderId));
    }
    /**
     * 接受保修
     */
    @PutMapping("/repairs/{orderId}/accept")
    public Result acceptRepair(@PathVariable Integer orderId){
        reparisService.updateRepairStatus(orderId);
        return Result.success("接受报修成功");
    }
    /**
     * 拒绝保修
     */
    @PutMapping("/repairs/{orderId}/reject")
    public Result rejectRepair(@PathVariable Integer orderId, @RequestParam String rejectReason){
        reparisService.regiectRepair(orderId,rejectReason);
        return Result.success("拒绝报修成功");
    }

    /**
     * 完成报修
     */
    @PutMapping("/repairs/{orderId}/complete")
    public Result completeRepair(@PathVariable Integer orderId){
        reparisService.completeRepair(orderId);
        return Result.success("完成报修成功");
    }

}
