package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;

import java.util.List;

public interface DormitoryService {

    List<GetAllBuildingVO> getAllBuilding();

    List<Integer> getFloorsByBuildingId(Integer buildingId);

    List<GetRoomsByBuildingAndFloorVO> getRoomsByBuildingAndFloor(Integer buildingId, Integer floorNumber);
}
