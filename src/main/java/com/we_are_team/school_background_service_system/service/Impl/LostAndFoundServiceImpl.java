package com.we_are_team.school_background_service_system.service.Impl;

import com.we_are_team.school_background_service_system.context.BaseContext;
import com.we_are_team.school_background_service_system.mapper.LostAndFoundMapper;
import com.we_are_team.school_background_service_system.pojo.dto.AddCategoryDTO;
import com.we_are_team.school_background_service_system.pojo.dto.AddLocationDTO;
import com.we_are_team.school_background_service_system.pojo.entity.ItemCategory;
import com.we_are_team.school_background_service_system.pojo.entity.LocationCategory;
import com.we_are_team.school_background_service_system.pojo.entity.LostAndFound;
import com.we_are_team.school_background_service_system.pojo.vo.ItemCategoryVO;
import com.we_are_team.school_background_service_system.pojo.vo.LocationCategoryVO;
import com.we_are_team.school_background_service_system.service.LostAndFoundService;
import com.we_are_team.school_background_service_system.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class LostAndFoundServiceImpl implements LostAndFoundService {
    @Autowired
    private LostAndFoundMapper lostAndFoundMapper;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Override
    public void addCategory(AddCategoryDTO addCategoryDTO) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setCategoryName(addCategoryDTO.getCategoryName());
        itemCategory.setCreatedTime(LocalDateTime.now());
        lostAndFoundMapper.addCategory(itemCategory);
    }

    @Override
    public void addLocation(AddLocationDTO addLocationDTO) {
        LocationCategory locationCategory = new LocationCategory();
        locationCategory.setLocationName(addLocationDTO.getLocationName());
        locationCategory.setCreatedTime(LocalDateTime.now());
        lostAndFoundMapper.addLocation(locationCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        lostAndFoundMapper.deleteCategory(categoryId);
    }

    @Override
    public void deleteLocation(Integer locationId) {
        lostAndFoundMapper.deleteLocation(locationId);
    }

    @Override
    public List<ItemCategoryVO> getAllItemCategory() {
        List<ItemCategoryVO> itemCategoryVOList = lostAndFoundMapper.getAllItemCategory();
        return itemCategoryVOList;
    }

    @Override
    public List<LocationCategoryVO> getAllLocationCategory() {
        List<LocationCategoryVO> locationCategoryVOList = lostAndFoundMapper.getAllLocationCategory();
        return locationCategoryVOList;
    }
/**
 * 上传失物招领信息
 */
    @Override
    public void uploadLostFound(String itemName, Integer categoryId, Integer locationId, String description, MultipartFile[] imageUrlsArray) {
        LostAndFound lostAndFound = new LostAndFound();
        lostAndFound.setItemName(itemName);
        lostAndFound.setAdminId(BaseContext.getCurrentId());
        lostAndFound.setCategoryId(categoryId);
        lostAndFound.setLocationId(locationId);
        lostAndFound.setDescription(description);
        lostAndFound.setCreatedTime(LocalDateTime.now());
        lostAndFound.setStatus(0);
        lostAndFound.setAdminId(BaseContext.getCurrentId());
        lostAndFound.setCreatedTime(LocalDateTime.now());
        List<String> urlsArray = new ArrayList<>();
        if(imageUrlsArray == null || imageUrlsArray.length == 0){
            lostAndFoundMapper.insert(lostAndFound);
            return;
        } else if ( imageUrlsArray.length> 0  && !imageUrlsArray[0].getOriginalFilename().equals("")) {
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
            lostAndFound.setImageUrls(result);
        }

            lostAndFoundMapper.insert(lostAndFound);

    }

    @Override
    public void updateLostFound(Integer itemId, String itemName, Integer categoryId, Integer locationId, String description, MultipartFile[] imageUrlsArray,Integer status) {
        LostAndFound lostAndFound = new LostAndFound();
        lostAndFound.setItemId(itemId);
        lostAndFound.setItemName(itemName);
        lostAndFound.setAdminId(BaseContext.getCurrentId());
        lostAndFound.setCategoryId(categoryId);
        lostAndFound.setLocationId(locationId);
        lostAndFound.setDescription(description);
        lostAndFound.setStatus(status);
        lostAndFound.setAdminId(BaseContext.getCurrentId());
        lostAndFound.setUpdatedTime(LocalDateTime.now());
        List<String> urlsArray = new ArrayList<>();
        if(imageUrlsArray == null || imageUrlsArray.length == 0){
            lostAndFoundMapper.updateLostFoundByItemId(lostAndFound);
            return;
        } else if ( imageUrlsArray.length> 0  && !imageUrlsArray[0].getOriginalFilename().equals("")) {
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
            lostAndFound.setImageUrls(result);
        }

        lostAndFoundMapper.updateLostFoundByItemId(lostAndFound);
    }

    @Override
    public void deleteLostFound(Integer itemId) {
         lostAndFoundMapper.deleteLostFound(itemId);
    }
}
