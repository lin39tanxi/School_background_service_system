package com.we_are_team.school_background_service_system.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.we_are_team.school_background_service_system.context.BaseContext;
import com.we_are_team.school_background_service_system.mapper.DormitoryMapper;
import com.we_are_team.school_background_service_system.mapper.UserMapper;
import com.we_are_team.school_background_service_system.pojo.dto.ChangeDormitoryDTO;
import com.we_are_team.school_background_service_system.pojo.dto.RejectionChangeDormitoryDTO;
import com.we_are_team.school_background_service_system.pojo.entity.ChangeDormitory;
import com.we_are_team.school_background_service_system.pojo.entity.DormRoom;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingAndFloorAndRoomsVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetFloorsByBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.service.DormitoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class DormitoryServiceImpl implements DormitoryService {
    @Autowired
    private DormitoryMapper dormitoryMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有宿舍楼栋
     * @return
     */
    @Override
    public List<GetAllBuildingVO> getAllBuilding() {
       return dormitoryMapper.getAllBuilding();
    }

    /**
     * 根据楼栋id获取所有楼层
     * @param buildingId
     * @return
     */
    @Override
    public List<GetFloorsByBuildingVO> getFloorsByBuildingId(Integer buildingId) {
        return dormitoryMapper.getFloorsByBuildingId(buildingId);
    }

    /**
     * 根据楼栋id和楼层id获取所有空宿舍
     * @param buildingId
     * @param floorId
     * @return
     */
    @Override
    public List<GetRoomsByBuildingAndFloorVO> getRoomsByBuildingAndFloor(Integer buildingId, Integer floorId) {
        return dormitoryMapper.getRoomsByBuildingAndFloor(buildingId, floorId);
    }

    @Override
    public List<GetAllBuildingAndFloorAndRoomsVO> getAllBuildingAndFloorAndRooms() {
        return dormitoryMapper.getAllBuildingAndFloorAndRooms();
    }
/**
 * 提交修改宿舍申请
 */
    @Override
    public void changeDormitory(ChangeDormitoryDTO changeDormitoryDTO) {
        ChangeDormitory changeDormitory = ChangeDormitory.builder()
                .createdTime(LocalDateTime.now())
                .status(0)
                .userId(BaseContext.getCurrentId())
                .dormChangeId(changeDormitoryDTO.getDormChangeId())
                .studentNumber(changeDormitoryDTO.getStudentNumber())
                .oldDormAddress(changeDormitoryDTO.getOldDormAddress())
                .newDormAddress(changeDormitoryDTO.getNewDormAddress())
                .buildingId(changeDormitoryDTO.getBuildingId())
                .floorId(changeDormitoryDTO.getFloorId())
                .roomId(changeDormitoryDTO.getRoomId())
                .reason(changeDormitoryDTO.getReason())
                .build();
        dormitoryMapper.insert(changeDormitory);
    }
/**
 * 同意修改宿舍申请
 */
    @Override
    public void agreeChangeDormitory(Integer dormChangeId) {
        ChangeDormitory changeDormitory = dormitoryMapper.getChangeDormitoryByChangeDormitoryId(dormChangeId);
        if(changeDormitory == null){
            throw new RuntimeException("不存在这个申请");
        }
//        根据学号查到原来的宿舍
        DormRoom dormRoom = dormitoryMapper.getDoomRoomByStudentNumber(changeDormitory.getStudentNumber());
//        删除到原来的学号，然后入住人数减1
        String StudentStr = dormRoom.getStudentArray();
        String[] oldStudentArray = StudentStr.split( ",");
        StringBuilder newStudentArray = new StringBuilder();
        for (String s : oldStudentArray) {
            if (!s.equals(changeDormitory.getStudentNumber())) {
                if (newStudentArray.length() > 0) {
                    newStudentArray.append(",");
                }
                newStudentArray.append(s);
            }
        }
        String result = newStudentArray.toString();
        log.info("更新前的宿舍：{}", dormRoom);
        Integer currentPeople = dormRoom.getCurrentPeople();
        dormRoom.setStudentArray(result);

        dormRoom.setCurrentPeople(currentPeople - 1);
        log.info("更新后的宿舍：{}", dormRoom);
        dormitoryMapper.update(dormRoom);

//        更新新宿舍的学号以及入住人数加1
        DormRoom newDormRoom = dormitoryMapper.getDoomRoomByRoomId(changeDormitory.getRoomId());
        String newStudentStr = newDormRoom.getStudentArray();
        newStudentStr = newStudentStr + "," + changeDormitory.getStudentNumber();
        newDormRoom.setStudentArray(newStudentStr);
        newDormRoom.setCurrentPeople(newDormRoom.getCurrentPeople() + 1);
        dormitoryMapper.update(newDormRoom);
//        更新学生表信息
        String building = dormitoryMapper.getBuildingNameByBuildingId(changeDormitory.getBuildingId());
        String dormitory = dormitoryMapper.getRoomNumberByRoomId(changeDormitory.getRoomId());
        userMapper.updateStudentInfoByStudentNumber(changeDormitory.getStudentNumber(),building,dormitory);
        Integer adminId = BaseContext.getCurrentId();
//        更新申请单信息
        dormitoryMapper.updateStatus(dormChangeId,adminId);




    }


    /**
     * 获取所有有空位的宿舍楼
     */

    @Override
    public List<GetAllBuildingVO> getEmptyBuilding() {
        return dormitoryMapper.getEmptyBuilding();
    }
    /**
     * 获取空宿舍楼
     */
    @Override
    public List<GetFloorsByBuildingVO> getEmptyFloorsByBuilding(Integer buildingId) {
        return dormitoryMapper.getEmptyFloorsByBuilding(buildingId);
    }
/**
 * 用户端获取空宿舍
 */
    @Override
    public List<GetRoomsByBuildingAndFloorVO> getEmptyRoomsByBuildingAndFloor(Integer buildingId, Integer floorId) {
        return dormitoryMapper.getEmptyRoomsByBuildingAndFloor(buildingId, floorId);
    }
/**
 * 拒绝修改宿舍申请
 */
    @Override
    public void rejectChangeDormitory(RejectionChangeDormitoryDTO rejectionChangeDormitoryDTO) {
        ChangeDormitory changeDormitory = ChangeDormitory.builder()
                .updatedTime(LocalDateTime.now())
                .status(2)
                .rejectionReason(rejectionChangeDormitoryDTO.getRejectionReason())
                .adminId(BaseContext.getCurrentId())
                .dormChangeId(rejectionChangeDormitoryDTO.getDormChangeId())
                .build();

      dormitoryMapper.updateChangeDormitory(changeDormitory);

    }

    @Override
    public PageResult getAllBuildingAndFloorAndRoomsInfo(Integer pageNum, Integer pageSize, String keyword, Integer buildingId, Integer floorId, Integer roomId,Integer gender) {
        PageHelper page = new PageHelper();
        page.startPage(pageNum,pageSize);
        Page<GetAllBuildingAndFloorAndRoomsVO> allBuildingAndFloorAndRooms = dormitoryMapper.getAllBuildingAndFloorAndRoomsInfo(keyword,buildingId,floorId,roomId,gender);
        return new PageResult(allBuildingAndFloorAndRooms.getTotal(),allBuildingAndFloorAndRooms);
    }


    @Override
    public PageResult getEmptyRoomsAndBuildingAndFloor(Integer pageNum, Integer pageSize, String keyword, Integer buildingId, Integer floorId, Integer roomId, Integer gender) {
        PageHelper page = new PageHelper();
        page.startPage(pageNum,pageSize);
        Page<GetAllBuildingAndFloorAndRoomsVO> allBuildingAndFloorAndRooms = dormitoryMapper.getAllEmptyBuildingAndFloorAndRoomsInfo(keyword,buildingId,floorId,roomId,gender);
        return new PageResult(allBuildingAndFloorAndRooms.getTotal(),allBuildingAndFloorAndRooms);
    }

    @Override
    public void cancelChangeDormitory(Integer dormChangeId) {
        dormitoryMapper.cancelChangeDormitory(dormChangeId);
    }

    @Override
    public void helpChangeDormitory(ChangeDormitoryDTO changeDormitoryDTO) {
        ChangeDormitory changeDormitory = ChangeDormitory.builder()
                .createdTime(LocalDateTime.now())
                .status(1)
                .userId(BaseContext.getCurrentId())
                .dormChangeId(changeDormitoryDTO.getDormChangeId())
                .studentNumber(changeDormitoryDTO.getStudentNumber())
                .oldDormAddress(changeDormitoryDTO.getOldDormAddress())
                .newDormAddress(changeDormitoryDTO.getNewDormAddress())
                .buildingId(changeDormitoryDTO.getBuildingId())
                .floorId(changeDormitoryDTO.getFloorId())
                .roomId(changeDormitoryDTO.getRoomId())
                .reason(changeDormitoryDTO.getReason())
                .adminId(BaseContext.getCurrentId())
                .updatedTime(LocalDateTime.now())
                .build();
        dormitoryMapper.insert(changeDormitory);
        DormRoom dormRoom = dormitoryMapper.getDoomRoomByStudentNumber(changeDormitory.getStudentNumber());
        //        删除到原来的学号，然后入住人数减1
        String StudentStr = dormRoom.getStudentArray();
        String[] oldStudentArray = StudentStr.split( ",");
        StringBuilder newStudentArray = new StringBuilder();
        for (String s : oldStudentArray) {
            if (!s.equals(changeDormitory.getStudentNumber())) {
                if (newStudentArray.length() > 0) {
                    newStudentArray.append(",");
                }
                newStudentArray.append(s);
            }
        }
        String result = newStudentArray.toString();
        log.info("更新前的宿舍：{}", dormRoom);
        Integer currentPeople = dormRoom.getCurrentPeople();
        dormRoom.setStudentArray(result);

        dormRoom.setCurrentPeople(currentPeople - 1);
        log.info("更新后的宿舍：{}", dormRoom);
        dormitoryMapper.update(dormRoom);

//        更新新宿舍的学号以及入住人数加1
        DormRoom newDormRoom = dormitoryMapper.getDoomRoomByRoomId(changeDormitory.getRoomId());
        String newStudentStr = newDormRoom.getStudentArray();
        newStudentStr = newStudentStr + "," + changeDormitory.getStudentNumber();
        newDormRoom.setStudentArray(newStudentStr);
        newDormRoom.setCurrentPeople(newDormRoom.getCurrentPeople() + 1);
        dormitoryMapper.update(newDormRoom);
        //        更新学生表信息
        String building = dormitoryMapper.getBuildingNameByBuildingId(changeDormitory.getBuildingId());
        String dormitory = dormitoryMapper.getRoomNumberByRoomId(changeDormitory.getRoomId());
        userMapper.updateStudentInfoByStudentNumber(changeDormitory.getStudentNumber(),building,dormitory);

    }

    @Override
    public PageResult getMyAllChangeDormitory(Integer pageNum, Integer pageSize, String keyword, Integer status, LocalDate beginTime, LocalDate endTime) {
        PageHelper page = new PageHelper();
        page.startPage(pageNum,pageSize);
        Integer userId = BaseContext.getCurrentId();
        Page<ChangeDormitory> myAllChangeDormitory =  dormitoryMapper.getMyAllChangeDormitory(userId,keyword,status,beginTime,endTime);
        return new PageResult(myAllChangeDormitory.getTotal(),myAllChangeDormitory);
    }
/**
 * 获取我的换宿舍申请信息
 * @param dormChangeId
 * @return
 */
    @Override
    public ChangeDormitory getMyChangeDormitoryInfo(Integer dormChangeId) {

        return dormitoryMapper.getMyChangeDormitoryInfo(dormChangeId);
    }

    @Override
    public PageResult getAllChangeDormitory(Integer pageNum, Integer pageSize, String keyword, Integer status, LocalDate beginTime, LocalDate endTime) {
        PageHelper page = new PageHelper();
        page.startPage(pageNum,pageSize);
        Integer userId = null;
        Page<ChangeDormitory> myAllChangeDormitory =  dormitoryMapper.getMyAllChangeDormitory(userId,keyword,status,beginTime,endTime);
        return new PageResult(myAllChangeDormitory.getTotal(),myAllChangeDormitory);
    }


}

