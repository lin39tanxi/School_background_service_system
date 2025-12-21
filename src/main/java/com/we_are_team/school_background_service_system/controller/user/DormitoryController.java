package com.we_are_team.school_background_service_system.controller.user;

import com.we_are_team.school_background_service_system.pojo.dto.ChangeDormitoryDTO;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetFloorsByBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/dormitory")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;
    /**
     * 更换宿舍申请
     * @return
     */
    @PostMapping("/changeDormitory")
    public Result changeDormitory(@RequestBody ChangeDormitoryDTO changeDormitoryDTO){
        dormitoryService.changeDormitory(changeDormitoryDTO);
        return Result.success("申请成功");
    }

    //    获取有空位的宿舍楼层
    @GetMapping("/getAllBuildingVO")
    public Result<List<GetAllBuildingVO>> getEmptyBuilding() {
        List<GetAllBuildingVO> emptyRoomFloor = dormitoryService.getEmptyBuilding();
        return Result.success("获取有空位的宿舍楼栋", emptyRoomFloor);
    }

    /**
     * 根据楼栋获取有空位楼层列表
     * @param buildingId
     * @return
     */
    @GetMapping("/getFloorsByBuilding")
    public Result<List<GetFloorsByBuildingVO>> getEmptyFloorsByBuilding(Integer buildingId) {
        List<GetFloorsByBuildingVO> floors = dormitoryService.getEmptyFloorsByBuilding(buildingId);
        return Result.success("获取楼层列表成功", floors);
    }

    /**
     * 根据楼栋和楼层获取宿舍列表
     * @param buildingId
     * @param floorId
     * @return
     */
//    GetRoomsByBuildingAndFloorVO
    @GetMapping("/getAllBuildingAndFloorAndRooms")
    public Result<PageResult> getEmptyRoomsByBuildingAndFloor(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")  Integer pageSize, String keyword, Integer buildingId, Integer floorId, Integer roomId, Integer gender) {
        PageResult pageResult  = dormitoryService.getEmptyRoomsByBuildingAndFloor(pageNum, pageSize, keyword, buildingId, floorId, roomId, gender);
        return Result.success("获取宿舍列表成功", pageResult);
    }

}
