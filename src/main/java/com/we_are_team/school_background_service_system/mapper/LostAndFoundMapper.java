package com.we_are_team.school_background_service_system.mapper;

import com.we_are_team.school_background_service_system.pojo.entity.ItemCategory;
import com.we_are_team.school_background_service_system.pojo.entity.LocationCategory;
import com.we_are_team.school_background_service_system.pojo.entity.LostAndFound;
import com.we_are_team.school_background_service_system.pojo.vo.ItemCategoryVO;
import com.we_are_team.school_background_service_system.pojo.vo.LocationCategoryVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LostAndFoundMapper {
    /**
     * 添加物品类别
     * @param  itemCategory
     */
    @Insert("insert into item_category(category_name, created_time) VALUES (#{itemCategory.categoryName},#{itemCategory.createdTime})")
    void addCategory(@Param("itemCategory") ItemCategory itemCategory);

    /**
     * 添加地点类别
     * @param  locationCategory
     */
    @Insert("insert into location_category(location_name, created_time) VALUES (#{locationCategory.locationName},#{locationCategory.createdTime})")
    void addLocation(@Param("locationCategory") LocationCategory locationCategory);

    @Delete("delete from item_category where category_id = #{categoryId}")
    void deleteCategory(@Param("categoryId") Integer categoryId);
/**
 * 删除地点类别
 * @param  locationId
 */
@Delete("delete from location_category where location_id = #{locationId}")
    void deleteLocation(@Param("locationId") Integer locationId);

 /**
 * 获取所有物品类别
 * @return
 */
 @Select("select * from item_category")
 List<ItemCategoryVO> getAllItemCategory();

   @Select("select * from location_category")
    List<LocationCategoryVO> getAllLocationCategory();

   @Insert("insert into lost_and_found(item_name, description,category_id, location_id, created_time, status,admin_id) " +
           "VALUES (#{lostAndFound.itemName}, #{lostAndFound.description}, #{lostAndFound.categoryId}, #{lostAndFound.locationId},  #{lostAndFound.createdTime}, #{lostAndFound.status},#{lostAndFound.adminId})")
    void insert(@Param("lostAndFound") LostAndFound lostAndFound);
 /**
 * 更新失物招领信息
 * @param lostAndFound
 */
    void updateLostFoundByItemId(@Param("lostAndFound") LostAndFound lostAndFound);

    @Delete("delete from lost_and_found where item_id = #{itemId}")
    void deleteLostFound(@Param( "itemId") Integer itemId);
}
