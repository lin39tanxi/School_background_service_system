package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.dto.ChangeDormitoryDTO;
import com.we_are_team.school_background_service_system.pojo.dto.RejectionChangeDormitoryDTO;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingAndFloorAndRoomsVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetFloorsByBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;
import com.we_are_team.school_background_service_system.result.PageResult;

import java.util.List;

public interface DormitoryService {

    List<GetAllBuildingVO> getAllBuilding();

    List<GetFloorsByBuildingVO> getFloorsByBuildingId(Integer buildingId);

    List<GetRoomsByBuildingAndFloorVO> getRoomsByBuildingAndFloor(Integer buildingId, Integer floorId);

    List<GetAllBuildingAndFloorAndRoomsVO> getAllBuildingAndFloorAndRooms();

    void changeDormitory(ChangeDormitoryDTO changeDormitoryDTO);

    void agreeChangeDormitory(Integer dormChangeId);


    List<GetAllBuildingVO> getEmptyBuilding();

    List<GetFloorsByBuildingVO> getEmptyFloorsByBuilding(Integer buildingId);
;

    List<GetRoomsByBuildingAndFloorVO> getEmptyRoomsByBuildingAndFloor(Integer buildingId, Integer floorId);

    void rejectChangeDormitory(RejectionChangeDormitoryDTO rejectionChangeDormitoryDTO);

    PageResult getAllBuildingAndFloorAndRoomsInfo(Integer pageNum, Integer pageSize, String keyword, Integer buildingId, Integer floorId, Integer roomId,Integer gender);

    PageResult getEmptyRoomsByBuildingAndFloor(Integer pageNum, Integer pageSize, String keyword, Integer buildingId, Integer floorId, Integer roomId, Integer gender);
}
