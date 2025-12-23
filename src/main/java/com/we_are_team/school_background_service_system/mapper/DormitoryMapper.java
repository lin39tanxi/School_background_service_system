package com.we_are_team.school_background_service_system.mapper;

import com.github.pagehelper.Page;
import com.we_are_team.school_background_service_system.pojo.entity.ApplicationForm;
import com.we_are_team.school_background_service_system.pojo.entity.ChangeDormitory;
import com.we_are_team.school_background_service_system.pojo.entity.DormRoom;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingAndFloorAndRoomsVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetFloorsByBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DormitoryMapper {
    @Select("select building_id, building_name ,gender from dorm_building")
    List<GetAllBuildingVO> getAllBuilding();

    @Select("select distinct floor_number ,floor_name, floor.floor_id " +
            "from dorm_building join floor on dorm_building.building_id = floor.building_id " +
            "where dorm_building.building_id = #{buildingId}")
    List<GetFloorsByBuildingVO> getFloorsByBuildingId(Integer buildingId);


    @Select("select room_id, room_number from dorm_room where building_id=#{buildingId} and floor_id= #{floorId}")
    List<GetRoomsByBuildingAndFloorVO> getRoomsByBuildingAndFloor(@Param("buildingId") Integer buildingId, @Param("floorId") Integer floorId);

    @Select("select dorm_building.building_id,dorm_building.gender,dorm_building.building_name,floor.floor_id,floor.floor_number,floor.floor_name,dorm_room.room_id,dorm_room.room_number,dorm_room.current_people,dorm_room.max_people from dorm_building left join floor on dorm_building.building_id = floor.building_id left join dorm_room on dorm_building.building_id = dorm_room.building_id")
    List<GetAllBuildingAndFloorAndRoomsVO> getAllBuildingAndFloorAndRooms();

    @Insert("insert into dorm_change(student_number,old_dorm_address,new_dorm_address,building_id,floor_id,room_id,status,created_time,reason,user_id,admin_id) " +
            "values (#{changeDormitory.studentNumber},#{changeDormitory.oldDormAddress},#{changeDormitory.newDormAddress},#{changeDormitory.buildingId},#{changeDormitory.floorId},#{changeDormitory.roomId},#{changeDormitory.status},#{changeDormitory.createdTime},#{changeDormitory.reason},#{changeDormitory.userId},#{changeDormitory.adminId})")
    void insert(@Param("changeDormitory") ChangeDormitory changeDormitory);

    @Select("select * from dorm_change where dorm_change_id=#{dormChangeId}")
    ChangeDormitory getChangeDormitoryByChangeDormitoryId(@Param("dormChangeId") Integer dormChangeId);

    @Select("select * from dorm_room where studentArray like concat('%', #{studentNumber}, '%')")
    DormRoom getDoomRoomByStudentNumber(String studentNumber);
    @Update("update dorm_room set studentArray=#{dormRoom.studentArray},current_people=#{dormRoom.currentPeople} where room_id=#{dormRoom.roomId}")
    void update(@Param("dormRoom") DormRoom dormRoom);

    @Select("select * from dorm_room where room_id= #{roomId}")
    DormRoom getDoomRoomByRoomId(String roomId);
     @Select("select building_name from dorm_building where building_id= #{buildingId}")
    String getBuildingNameByBuildingId(String buildingId);

     @Select("select room_number from dorm_room where room_id= #{roomId}")
    String getRoomNumberByRoomId(String roomId);
    @Update("update dorm_change set status=1,dorm_change.admin_id = #{adminId} where dorm_change_id=#{dormChangeId}")
    void updateStatus(@Param("dormChangeId") Integer dormChangeId,@Param("adminId") Integer adminId);

/**
     * 获取所有空宿舍
     * @return
     */
    @Select("select distinct dorm_building.building_id, building_name ,gender  from dorm_building left join dorm_room on dorm_building.building_id = dorm_room.building_id where dorm_room.current_people<dorm_room.max_people")
    List<GetAllBuildingVO> getEmptyBuilding();

    /**
     * 获取到所有有空位的楼层
     * @param buildingId
     * @return
     */
    @Select("select distinct floor_number ,floor_name, floor.floor_id from dorm_building left join floor on dorm_building.building_id = floor.building_id left join dorm_room on floor.floor_id = dorm_room.floor_id where dorm_building.building_id = #{buildingId} and dorm_room.current_people<dorm_room.max_people")
    List<GetFloorsByBuildingVO> getEmptyFloorsByBuilding(Integer buildingId);
/**
     * 获取所有空宿舍的宿舍号
     * @param buildingId
     * @param floorId
     * @return
     */
    @Select("select room_id, room_number from dorm_room where building_id=#{buildingId} and floor_id= #{floorId} and current_people<max_people")
    List<GetRoomsByBuildingAndFloorVO> getEmptyRoomsByBuildingAndFloor(@Param("buildingId") Integer buildingId,@Param("floorId") Integer floorId);

    @Update("UPDATE dorm_change SET status = #{changeDormitory.status}, rejection_reason = #{changeDormitory.rejectionReason}, admin_id = #{changeDormitory.adminId}, updated_time = #{changeDormitory.updatedTime} WHERE dorm_change_id = #{changeDormitory.dormChangeId}")
    void updateChangeDormitory(@Param("changeDormitory") ChangeDormitory changeDormitory);

    Page<GetAllBuildingAndFloorAndRoomsVO> getAllBuildingAndFloorAndRoomsInfo(@Param("keyword") String keyword,@Param("buildingId") Integer buildingId,@Param("floorId") Integer floorId,@Param("roomId") Integer roomId,@Param("gender") Integer gender);

    Page<GetAllBuildingAndFloorAndRoomsVO> getAllEmptyBuildingAndFloorAndRoomsInfo(@Param("keyword") String keyword,@Param("buildingId") Integer buildingId,@Param("floorId") Integer floorId,@Param("roomId") Integer roomId,@Param("gender") Integer gender);

    @Update("UPDATE dorm_change SET status = 4 WHERE dorm_change_id = #{dormChangeId}")
    void cancelChangeDormitory(@Param("changeDormitory") Integer dormChangeId);

    Page<ChangeDormitory> getMyAllChangeDormitory(@Param("userId") Integer userId, @Param("keyword") String keyword, @Param("status") Integer status, @Param("beginTime")LocalDate beginTime, @Param("endTime")LocalDate endTime);

    @Select("SELECT * FROM dorm_change WHERE dorm_change_id = #{dormChangeId}")
    ChangeDormitory getMyChangeDormitoryInfo(Integer dormChangeId);

    @Select("select dorm_change_id as id,reason as content,created_time,updated_time,status from dorm_change where  user_id = #{userId}")
    List<ApplicationForm> getAppcationFormByUserId(Integer userId);
}
