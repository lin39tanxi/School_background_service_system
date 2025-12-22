package com.we_are_team.school_background_service_system.controller.user;

import com.we_are_team.school_background_service_system.pojo.vo.ItemCategoryVO;
import com.we_are_team.school_background_service_system.pojo.vo.LocationCategoryVO;
import com.we_are_team.school_background_service_system.pojo.vo.LostAndFoundVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.LostAndFoundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequestMapping("/user/lostAndFound")
@RestController
public class LostAndFoundController {
    @Autowired
    private LostAndFoundService lostAndFoundService;

    /**
     * 获取失物招领列表
     */
    @GetMapping("/getLostAndFoundList")
    public Result<PageResult> getLostAndFoundList(String status ,
                                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  String orderKey,
                                                  @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime,
                                                  @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime)
    {      log.info("获取失物招领列表,{}",status);
        return Result.success("获取成功",lostAndFoundService.getLostAndFoundList(pageNum, pageSize, orderKey, beginTime, endTime,status));
    }

    /**
     * 获取失物招领详情
     */
    @GetMapping("/getLostAndFoundDetail/{itemId}")
    public Result<LostAndFoundVO> getLostAndFoundDetail(@PathVariable Integer itemId){
        return Result.success("获取失物招领物品详情成功",lostAndFoundService.getLostAndFoundDetail(itemId));
    }
//    获取失物招领物品分类列表
@GetMapping("/getAllItemCategory")
public Result<List<ItemCategoryVO>> getAllItemCategory(){
    return Result.success("获取所有分类成功",lostAndFoundService.getAllItemCategory());
}
    /**
     * 获取所有地点分类
     */
    @GetMapping("/getAllLocationCategory")
    public Result<List<LocationCategoryVO>> getAllLocationCategory(){
        return Result.success("获取所有地点成功",lostAndFoundService.getAllLocationCategory());
    }
}
