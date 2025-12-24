package com.we_are_team.school_background_service_system.mapper;

import com.github.pagehelper.Page;
import com.we_are_team.school_background_service_system.pojo.entity.ApplicationForm;
import com.we_are_team.school_background_service_system.pojo.entity.RepairOrder;
import com.we_are_team.school_background_service_system.pojo.vo.GetRepairOrderListVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface RepairsMapper {

    /**
     * 添加报修单
     */

    @Insert("insert into order_table (user_id, process_status, description,image_urls, created_time, address, phone,appointment_begin,appointment_end) " +
            "values(#{repairOrder.userId},#{repairOrder.processStatus},#{repairOrder.description},#{repairOrder.imageUrls},#{repairOrder.createdTime},#{repairOrder.address},#{repairOrder.phone},#{repairOrder.appointmentBegin},#{repairOrder.appointmentEnd})")
    void insert(@Param("repairOrder") RepairOrder repairOrder);

//    @Insert("insert into order_table (user_id, process_status, description, admin_id, reject_reason, completed_time, image_urls, created_time, updated_time, rating, comment, comment_created_time,address,phone) " +
//            "values(#{userId},#{processStatus},#{description},#{adminId},#{rejectReason},#{completedTime},#{imageUrls},#{createdTime},#{updatedTime},#{rating},#{comment},#{commentCreatedTime},#{address},#{phone})")
//    void insert(RepairOrder repairOrder);

    Page<GetRepairOrderListVO> getMyRepairs(@Param("userId") Integer userId, @Param("processStatus") String processStatus, @Param("orderKey") String orderKey, @Param("beginTime")LocalDate beginTime, @Param("endTime")LocalDate endTime);
/**
 * 获取报修单详情
 */
@Select("select order_id, " +
        "user_id, " +
        "process_status, " +
        "description, admin_id, " +
        "reject_reason, completed_time, " +
        "image_urls, created_time, " +
        "updated_time, " +
        "rating, " +
        "comment, " +
        "comment_created_time, " +
        "address, " +
        "phone, " +
        "appointment_begin as appointment_begin," +
        " appointment_end as appointment_end " +
        "from order_table" +
        " where order_id = #{orderId}")
RepairOrder getRepairDetail(Integer orderId);

    void updateRepairStatus(@Param("repairOrder") RepairOrder repairOrder);

    @Update("update order_table set comment = #{comment}, rating = #{rating}, comment_created_time = #{commentCreatedTime},process_status= #{processStatus} where order_id = #{orderId}")
    void updateComment(@Param("orderId") Integer orderId,@Param("comment") String  comment,@Param("rating") Integer rating, @Param("commentCreatedTime") LocalDateTime commentCreatedTime,@Param("processStatus") Integer processStatus);

    @Update("update order_table set rating = null,process_status = 3,comment = null ,order_table.comment_created_time = null where order_id = #{orderId}")
    void deleteComment(Integer orderId);

    @Select("select order_id as id,description as content,created_time ,updated_time,process_status as status from order_table where user_id = #{userId}")
    List<ApplicationForm> getAppcationFormByUserId(Integer userId);
}
