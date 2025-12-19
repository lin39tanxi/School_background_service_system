package com.we_are_team.school_background_service_system.service.Impl;

import com.we_are_team.school_background_service_system.mapper.DormitoryMapper;
import com.we_are_team.school_background_service_system.pojo.vo.GetAllBuildingVO;
import com.we_are_team.school_background_service_system.pojo.vo.GetRoomsByBuildingAndFloorVO;
import com.we_are_team.school_background_service_system.service.DormitoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DormitoryServiceImpl implements DormitoryService {
    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Override
    public List<GetAllBuildingVO> getAllBuilding() {
       return dormitoryMapper.getAllBuilding();
    }

    @Override
    public List<Integer> getFloorsByBuildingId(Integer buildingId) {
        return dormitoryMapper.getFloorsByBuildingId(buildingId);
    }

    @Override
    public List<GetRoomsByBuildingAndFloorVO> getRoomsByBuildingAndFloor(Integer buildingId, Integer floorNumber) {
        return dormitoryMapper.getRoomsByBuildingAndFloor(buildingId, floorNumber);
    }
}

