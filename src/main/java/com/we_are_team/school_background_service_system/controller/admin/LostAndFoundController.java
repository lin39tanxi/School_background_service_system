package com.we_are_team.school_background_service_system.controller.admin;

import com.we_are_team.school_background_service_system.pojo.dto.AddCategoryDTO;
import com.we_are_team.school_background_service_system.pojo.dto.AddLocationDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController("AdminLostAndFoundController")
@RequestMapping("/admin/lostAndFound")
public class LostAndFoundController {

    @Autowired
    private LostAndFoundService lostAndFoundService;

    /**
     * 添加物品分类
     */
    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody AddCategoryDTO addCategoryDTO){
        log.info("添加分类,{}", addCategoryDTO);
        lostAndFoundService.addCategory(addCategoryDTO);
        return Result.success("添加分类成功");
    }
/**
 * 删除分类
 */
   @DeleteMapping("/deleteCategory/{categoryId}")
    public Result deleteCategory(@PathVariable Integer categoryId){
       lostAndFoundService.deleteCategory(categoryId);
       return Result.success("删除分类成功");
   }
    /**
     * 添加地点分类
     */
    @PostMapping("/addLocation")
    public Result addLocation(@RequestBody AddLocationDTO addLocationDTO){
        log.info("添加分类,{}", addLocationDTO);
        lostAndFoundService.addLocation(addLocationDTO);
        return Result.success("添加分类成功");
    }

    /**
     * 删除地点分类
     */
    @DeleteMapping("/deleteLocation/{locationId}")
    public Result deleteLocation(@PathVariable Integer locationId){
        lostAndFoundService.deleteLocation(locationId);
        return Result.success("删除地点成功");
    }
/**
 * 获取所有物品分类
 */
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
    /**
     * 上传失物
     */
    @PostMapping("/uploadLostFound")
    public Result lostFound(
            @RequestParam String itemName,
            @RequestParam Integer categoryId,
            @RequestParam Integer locationId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) MultipartFile[] imageUrlsArray
            ){
        lostAndFoundService.uploadLostFound(itemName,categoryId,locationId,description,imageUrlsArray);
        return Result.success("上传成功");
    }
    /**
     * 更新失物招领信息
     */
    @PutMapping("/updateLostFound")
    public Result updateLostFound( @RequestParam Integer itemId,
                                    @RequestParam String itemName,
                                   @RequestParam Integer categoryId,
                                   @RequestParam Integer locationId,
                                   @RequestParam Integer status,
                                   @RequestParam(required = false) String description,
                                   @RequestParam(required = false) MultipartFile[] imageUrlsArray)
        {
        lostAndFoundService.updateLostFound(itemId,itemName,categoryId,locationId,description,imageUrlsArray,status);
        return Result.success("更新成功");
    }
    /**
     * 删除失物招领信息
     */
    @DeleteMapping("/deleteLostFound/{itemId}")
    public Result deleteLostFound(@PathVariable Integer itemId){
        lostAndFoundService.deleteLostFound(itemId);
        return Result.success("删除成功");
    }
    /**
     * 获取失物招领列表
     */
    @GetMapping("/getLostAndFoundList")
    public Result<PageResult> getLostAndFoundList(String status ,
                                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  String orderKey,
                                                  @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime,
                                                  @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime,
                                                  Integer categoryId,
                                                  Integer locationId)
    {      log.info("获取失物招领列表,{}",status);
           return Result.success("获取成功",lostAndFoundService.getLostAndFoundList(pageNum, pageSize, orderKey, beginTime, endTime,status,categoryId,locationId));
    }

    /**
     * 获取失物招领详情
     */
    @GetMapping("/getLostAndFoundDetail/{itemId}")
    public Result<LostAndFoundVO> getLostAndFoundDetail(@PathVariable Integer itemId){
        return Result.success("获取失物招领物品详情成功",lostAndFoundService.getLostAndFoundDetail(itemId));
    }
}
