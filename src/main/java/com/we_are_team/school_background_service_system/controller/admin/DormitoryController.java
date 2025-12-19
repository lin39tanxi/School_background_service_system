package com.we_are_team.school_background_service_system.controller.admin;

import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/dormitory")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;
//    获取所有的宿舍楼栋
    @GetMapping("/getAllBuilding")
    public Result<List<GetAllBuildingVO>> getAllBuilding(){
        dormitoryService.getAllBuilding();
        return Result.success("获取宿舍楼栋列表成功",dormitoryService.getAllBuilding());

    }
    @GetMapping("/getFloorsByBuilding")
    public Result<List<Integer>> getFloorsByBuilding(Integer buildingId) {
        List<Integer> floors = dormitoryService.getFloorsByBuildingId(buildingId);
        return Result.success("获取楼层列表成功", floors);
    }

    @GetMapping("/getRoomsByBuildingAndFloor")
    public Result<List<GetRoomsByBuildingAndFloorVO>> getRoomsByBuildingAndFloor(Integer buildingId, Integer floorNumber) {
        List<GetRoomsByBuildingAndFloorVO> rooms = dormitoryService.getRoomsByBuildingAndFloor(buildingId, floorNumber);
        return Result.success("获取宿舍列表成功", rooms);
    }

}

