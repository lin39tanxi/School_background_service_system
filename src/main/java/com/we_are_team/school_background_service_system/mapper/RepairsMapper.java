package com.we_are_team.school_background_service_system.mapper;

import com.github.pagehelper.Page;
import com.we_are_team.school_background_service_system.pojo.entity.RepairOrder;
import com.we_are_team.school_background_service_system.pojo.vo.GetRepairOrderVO;
import com.we_are_team.school_background_service_system.pojo.vo.RepairOrderVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;

@Mapper
public interface RepairsMapper {

    /**
     * 添加报修单
     */

    @Insert("insert into order_table (user_id, process_status, description, admin_id, reject_reason, completed_time, image_urls, created_time, updated_time, rating, comment, comment_created_time) " +
            "values(#{userId},#{processStatus},#{description},#{adminId},#{rejectReason},#{completedTime},#{imageUrls},#{createdTime},#{updatedTime},#{rating},#{comment},#{commentCreatedTime})")
    void insert(RepairOrder repairOrder);


    Page<GetRepairOrderVO> getMyRepairs(@Param("userId") Integer userId, @Param("processStatus") String processStatus, @Param("orderKey") String orderKey, @Param("beginTime")LocalDate beginTime, @Param("endTime")LocalDate endTime);
/**
 * 获取报修单详情
 */
    @Select("select * from order_table where order_id = #{orderId}")
    RepairOrder getRepairDetail(Integer orderId);

    @Update("update order_table set process_status = #{processStatus} where order_id = #{orderId}")
    void cancelRepair(@Param("orderId") Integer orderId,@Param("processStatus") Integer processStatus);
}
