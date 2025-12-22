package com.we_are_team.school_background_service_system.service;

import com.we_are_team.school_background_service_system.pojo.dto.ChangeDormitoryDTO;
import com.we_are_team.school_background_service_system.pojo.dto.RejectionChangeDormitoryDTO;
import com.we_are_team.school_background_service_system.pojo.entity.ChangeDormitory;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingAndFloorAndRoomsVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetFloorsByBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;
import com.we_are_team.school_background_service_system.result.PageResult;

import java.time.LocalDate;
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

    /**
     * 获取宿舍列表
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @param buildingId
     * @param floorId
     * @param roomId
     * @param gender
     * @return
     */
    PageResult getAllBuildingAndFloorAndRoomsInfo(Integer pageNum, Integer pageSize, String keyword, Integer buildingId, Integer floorId, Integer roomId,Integer gender);


    PageResult getEmptyRoomsAndBuildingAndFloor(Integer pageNum, Integer pageSize, String keyword, Integer buildingId, Integer floorId, Integer roomId, Integer gender);
/**
 * 取消换宿舍申请
 *
 */
    void cancelChangeDormitory(Integer dormChangeId);
    /**
     * 帮助换宿舍申请
     *
     */
    void helpChangeDormitory(ChangeDormitoryDTO changeDormitoryDTO);


    PageResult getMyAllChangeDormitory(Integer pageNum, Integer pageSize, String keyword, Integer status, LocalDate beginTime, LocalDate endTime);
/**
 * 获取我的换宿舍申请详细信息
 *
 */
    ChangeDormitory getMyChangeDormitoryInfo(Integer dormChangeId);
  /**
 * 获取所有换宿舍申请信息列表
 *
 */
    PageResult getAllChangeDormitory(Integer pageNum, Integer pageSize, String keyword, Integer status, LocalDate beginTime, LocalDate endTime);
}
