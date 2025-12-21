package com.we_are_team.school_background_service_system.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DormBuilding {
    private Integer id;
    private String buildingName;
    private String totalPeople;
    private String cuttentCount;
    private LocalDateTime createTime;
}
