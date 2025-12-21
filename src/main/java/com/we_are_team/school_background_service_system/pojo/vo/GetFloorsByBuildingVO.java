package com.we_are_team.school_background_service_system.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFloorsByBuildingVO {
    private String floorId;
    private Integer floorNumber;
    private String floorName;
}
