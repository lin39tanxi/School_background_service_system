package com.we_are_team.school_background_service_system.mapper;

import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DormitoryMapper {
    @Select("select building_id, building_name from dorm_building")
    List<GetAllBuildingVO> getAllBuilding();

    @Select("select distinct floor_number from dorm_room join dorm_building on dorm_room.building_id = dorm_building.building_id where dorm_room.building_id = #{buildingId}")
    List<Integer> getFloorsByBuildingId(Integer buildingId);

    @Select("select distinct room_id ,room_number from dorm_room join dorm_building on dorm_room.building_id = dorm_building.building_id where dorm_room.building_id = #{buildingId} and floor_number=#{floorNumber}")
    List<GetRoomsByBuildingAndFloorVO> getRoomsByBuildingAndFloor(@Param("buildingId") Integer buildingId, @Param("floorNumber") Integer floorNumber);
}
