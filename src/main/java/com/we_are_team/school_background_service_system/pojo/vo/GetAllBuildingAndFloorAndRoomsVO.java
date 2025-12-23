package com.we_are_team.school_background_service_system.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllBuildingAndFloorAndRoomsVO {
    private String buildingId;
    private String buildingName;
    private String floorId;
    private String floorName;
    private String roomId;
    private String roomNumber;
    private Integer currentPeople;
    private Integer maxPeople;
    private Integer gender;
    private String studentArray;
}
