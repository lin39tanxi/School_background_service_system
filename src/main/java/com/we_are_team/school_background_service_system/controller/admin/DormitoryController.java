package com.we_are_team.school_background_service_system.controller.admin;

import com.we_are_team.school_background_service_system.pojo.dto.ChangeDormitoryDTO;
import com.we_are_team.school_background_service_system.pojo.dto.RejectionChangeDormitoryDTO;
import com.we_are_team.school_background_service_system.pojo.entity.ChangeDormitory;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingAndFloorAndRoomsVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetFloorsByBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController("adminDormitoryController")
@RequestMapping("/admin/dormitory")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;

    //    获取所有的宿舍楼栋
    @GetMapping("/getAllBuilding")
    public Result<List<GetAllBuildingVO>> getAllBuilding() {
        dormitoryService.getAllBuilding();
        return Result.success("获取宿舍楼栋列表成功", dormitoryService.getAllBuilding());

    }

    /**
     * 根据楼栋获取楼层列表
     *
     * @param buildingId
     * @return
     */
    @GetMapping("/getFloorsByBuilding")
    public Result<List<GetFloorsByBuildingVO>> getFloorsByBuilding(Integer buildingId) {
        List<GetFloorsByBuildingVO> floors = dormitoryService.getFloorsByBuildingId(buildingId);
        return Result.success("获取楼层列表成功", floors);
    }

    /**
     * 根据楼栋和楼层获取宿舍列表
     *
     * @param buildingId
     * @param floorId
     * @return
     */
    @GetMapping("/getRoomsByBuildingAndFloor")
    public Result<List<GetRoomsByBuildingAndFloorVO>> getRoomsByBuildingAndFloor(Integer buildingId, Integer floorId) {
        List<GetRoomsByBuildingAndFloorVO> rooms = dormitoryService.getRoomsByBuildingAndFloor(buildingId, floorId);
        return Result.success("获取宿舍列表成功", rooms);
    }

    /**
     * 获取所有的楼栋、楼层和宿舍列表,以及可入住人数和已入住人数
     *
     * @return
     */
//    @GetMapping("/getAllBuildingAndFloorAndRooms")
//    public Result<List<GetAllBuildingAndFloorAndRoomsVO>> getAllBuildingAndFloorAndRooms() {
//        List<GetAllBuildingAndFloorAndRoomsVO> allBuildingAndFloorAndRooms = dormitoryService.getAllBuildingAndFloorAndRooms();
//        return Result.success("获取所有楼栋、楼层和宿舍列表成功", allBuildingAndFloorAndRooms);
//    }
    @GetMapping("/getAllBuildingAndFloorAndRooms")
    public Result<PageResult> getAllBuildingAndFloorAndRooms(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, Integer buildingId, Integer floorId, Integer roomId, Integer gender) {
        PageResult pageResult = dormitoryService.getAllBuildingAndFloorAndRoomsInfo(pageNum, pageSize, keyword, buildingId, floorId, roomId, gender);
        return Result.success("获取所有楼栋、楼层和宿舍列表成功", pageResult);
    }


    //    同意更换宿舍申请
    @PutMapping("/agreeChangeDormitory/{dormChangeId}")
    public Result agreeChangeDormitory(@PathVariable Integer dormChangeId) {
        dormitoryService.agreeChangeDormitory(dormChangeId);
        return Result.success("同意更换宿舍申请成功");
    }

    // 拒绝宿舍申请
    @PostMapping("/rejectChangeDormitory")
    public Result rejectChangeDormitory(@RequestBody RejectionChangeDormitoryDTO rejectionChangeDormitoryDTO) {
        dormitoryService.rejectChangeDormitory(rejectionChangeDormitoryDTO);
        return Result.success("拒绝更换宿舍申请成功");
    }

    /**
     * 帮助学生更换宿舍
     */
    @PostMapping("/helpChangeDormitory")
    public Result changeDormitory(@RequestBody ChangeDormitoryDTO changeDormitoryDTO) {
        dormitoryService.helpChangeDormitory(changeDormitoryDTO);
        return Result.success("更换宿舍成功成功");
    }

    /**
     * 获取所有学生更换宿舍申请
     */
    @GetMapping("/getAllChangeDormitory")
    public Result<PageResult> getAllChangeDormitory(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, Integer status, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime) {
        PageResult pageResult = dormitoryService.getAllChangeDormitory(pageNum, pageSize, keyword, status, beginTime, endTime);
        return Result.success("获取申请列表成功", pageResult);

    }

    /**
     * 获取申请的详情
     */
    @GetMapping("/getMyChangeDormitoryInfo/{dormChangeId}")
    public Result<ChangeDormitory> getMyChangeDormitoryInfo(@PathVariable Integer dormChangeId) {
        ChangeDormitory changeDormitory = dormitoryService.getMyChangeDormitoryInfo(dormChangeId);
        return Result.success("获取申请单详情成功", changeDormitory);
    }
}






