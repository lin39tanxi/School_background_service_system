package com.we_are_team.school_background_service_system.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRoomsByBuildingAndFloorVO {
    private String roomId;
    private String roomNumber;
}
