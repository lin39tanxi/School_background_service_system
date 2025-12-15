package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.dto.AddCategoryDTO;
import com.we_are_team.school_background_service_system.pojo.dto.AddLocationDTO;
import com.we_are_team.school_background_service_system.pojo.vo.ItemCategoryVO;
import com.we_are_team.school_background_service_system.pojo.vo.LocationCategoryVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LostAndFoundService {
    /**
     * 添加分类
     * @param
     */
    void addCategory(AddCategoryDTO addCategoryDTO);
    /**
     * 添加地点
     * @param
     */
    void addLocation(AddLocationDTO addLocationDTO);
    /**
     * 删除分类
     * @param
     */
    void deleteCategory(Integer categoryId);
    /**
     * 删除地点
     * @param
     */
    void deleteLocation(Integer locationId);
    /**
     * 获取所有分类
     * @param
     */
    List<ItemCategoryVO> getAllItemCategory();
    /**
     * 获取所有地点
     * @param
     */
    List<LocationCategoryVO> getAllLocationCategory();

    /**
     * 上传失物
     * @param
     */
    void uploadLostFound(String itemName, Integer categoryId, Integer locationId, String description, MultipartFile[] imageUrlsArray);
/**
     * 修改失物
     * @param
     */
    void updateLostFound(Integer itemId, String itemName, Integer categoryId, Integer locationId, String description, MultipartFile[] imageUrlsArray,Integer status);
/**
     * 删除失物
     * @param
     */
    void deleteLostFound(Integer itemId);
}
