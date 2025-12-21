package com.we_are_team.school_background_service_system.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.we_are_team.school_background_service_system.context.BaseContext;
import com.we_are_team.school_background_service_system.mapper.LostAndFoundMapper;
import com.we_are_team.school_background_service_system.mapper.UserMapper;
import com.we_are_team.school_background_service_system.pojo.dto.AddCategoryDTO;
import com.we_are_team.school_background_service_system.pojo.dto.AddLocationDTO;
import com.we_are_team.school_background_service_system.pojo.entity.ItemCategory;
import com.we_are_team.school_background_service_system.pojo.entity.LocationCategory;
import com.we_are_team.school_background_service_system.pojo.entity.LostAndFound;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import com.we_are_team.school_background_service_system.pojo.vo.GetLostAndFoundVO;
import com.we_are_team.school_background_service_system.pojo.vo.ItemCategoryVO;
import com.we_are_team.school_background_service_system.pojo.vo.LocationCategoryVO;
import com.we_are_team.school_background_service_system.pojo.vo.LostAndFoundVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.service.LostAndFoundService;
import com.we_are_team.school_background_service_system.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@Slf4j
@Service
public class LostAndFoundServiceImpl implements LostAndFoundService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LostAndFoundMapper lostAndFoundMapper;
    @Autowired
    private AliOssUtil aliOssUtil;


    @Override
    public void addCategory(AddCategoryDTO addCategoryDTO) {
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("5")){
            throw new RuntimeException("没有权限添加物品分类");
        }

        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setCategoryName(addCategoryDTO.getCategoryName());
        itemCategory.setCreatedTime(LocalDateTime.now());
        lostAndFoundMapper.addCategory(itemCategory);
    }

    @Override
    public void addLocation(AddLocationDTO addLocationDTO) {
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("5")){
            throw new RuntimeException("没有权限添加地点分类");
        }
        LocationCategory locationCategory = new LocationCategory();
        locationCategory.setLocationName(addLocationDTO.getLocationName());
        locationCategory.setCreatedTime(LocalDateTime.now());
        lostAndFoundMapper.addLocation(locationCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("5")){
            throw new RuntimeException("没有权限删除物品分类");
        }
        lostAndFoundMapper.deleteCategory(categoryId);
    }

    @Override
    public void deleteLocation(Integer locationId) {
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("5")){
            throw new RuntimeException("没有权限删除地点分类");
        }
        lostAndFoundMapper.deleteLocation(locationId);
    }

    @Override
    public List<ItemCategoryVO> getAllItemCategory() {
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("5") && !user.getPermission().contains("0")){
            throw new RuntimeException("没有权限查看物品分类");
        }
        List<ItemCategoryVO> itemCategoryVOList = lostAndFoundMapper.getAllItemCategory();
        return itemCategoryVOList;
    }

    @Override
    public List<LocationCategoryVO> getAllLocationCategory() {
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("5") && !user.getPermission().contains("0")){
            throw new RuntimeException("没有权限查看地点分类");
        }
        List<LocationCategoryVO> locationCategoryVOList = lostAndFoundMapper.getAllLocationCategory();
        return locationCategoryVOList;
    }
/**
 * 上传失物招领信息
 */
    @Override
    public void uploadLostFound(String itemName, Integer categoryId, Integer locationId, String description, MultipartFile[] imageUrlsArray) {
        if(itemName == null || itemName.equals("")
           || categoryId == null || categoryId == 0
           || locationId == null || locationId == 0
              ){
            throw new RuntimeException("请填写完整信息");
        }
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("5")){
            throw new RuntimeException("没有权限上传失物招领信息");
        }
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
        if(itemName == null || itemName.isEmpty()
                || categoryId == null || categoryId == 0
                || locationId == null || locationId == 0
        ){
            throw new RuntimeException("请填写完整信息");
        }
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("5")){
            throw new RuntimeException("没有权限修改失物招领信息");
        }
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
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("5")){
            throw new RuntimeException("没有权限删除失物招领信息");
        }
         lostAndFoundMapper.deleteLostFound(itemId);
    }

    @Override
    public PageResult getLostAndFoundList(Integer pageNum, Integer pageSize, String orderKey, LocalDate beginTime, LocalDate endTime, String status) {
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if (!user.getPermission().contains("0") && !user.getPermission().contains("5")){
            throw new RuntimeException("没有权限查看失物招领信息");
        }
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum, pageSize);
        Page<GetLostAndFoundVO> lostAndFoundVOList = lostAndFoundMapper.getLostAndFoundList(orderKey,beginTime,endTime,status);
        lostAndFoundVOList.forEach(lostAndFoundVO -> {
            if(lostAndFoundVO.getImageUrls() != null && !lostAndFoundVO.getImageUrls().isEmpty()){
                String[] imageUrls = lostAndFoundVO.getImageUrls().split(",");
                lostAndFoundVO.setImageUrls(imageUrls[0]);
            }
        });
        return new PageResult(lostAndFoundVOList.getTotal(),lostAndFoundVOList.getResult());
    }

    @Override
    public LostAndFoundVO getLostAndFoundDetail(Integer itemId) {
        User user =  userMapper.getUserByUserId(BaseContext.getCurrentId());
        if (!user.getPermission().contains("0") && !user.getPermission().contains("5")){
            throw new RuntimeException("没有权限查看失物招领信息");
        }
        LostAndFound lostAndFound = lostAndFoundMapper.getLostAndFoundDetail(itemId);
        LostAndFoundVO lostAndFoundVO = new LostAndFoundVO();
        BeanUtils.copyProperties(lostAndFound,lostAndFoundVO);
        String[] imageUrls = null;
        if(lostAndFound.getImageUrls()!=null && !lostAndFound.getImageUrls().isEmpty()){
            imageUrls = lostAndFound.getImageUrls().split(",");
            lostAndFoundVO.setImageUrls(Arrays.asList(imageUrls));
        }

        return lostAndFoundVO;
    }
}
